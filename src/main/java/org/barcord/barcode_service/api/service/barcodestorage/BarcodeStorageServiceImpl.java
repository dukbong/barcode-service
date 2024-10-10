package org.barcord.barcode_service.api.service.barcodestorage;

import lombok.RequiredArgsConstructor;
import org.barcord.barcode_service.api.controller.barcodestorage.request.BarcodeStorageRequest;
import org.barcord.barcode_service.api.service.barcodestorage.dto.BarcodeStorageServiceRequest;
import org.barcord.barcode_service.domain.barcodestorage.BarcodeStorage;
import org.barcord.barcode_service.domain.barcodestorage.BarcodeStorageRepository;
import org.barcord.barcode_service.domain.user.User;
import org.barcord.barcode_service.domain.user.UserRepository;
import org.barcord.barcode_service.exception.ServerNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BarcodeStorageServiceImpl implements BarcodeStorageService {

    private final BarcodeStorageRepository barcodeStorageRepository;
    private final UserRepository userRepository;

    @Override
    public List<BarcodeStorage> getBarcodesByUser(BarcodeStorageServiceRequest barcodeStorageServiceRequest) {
        List<BarcodeStorage> barcodesToGet = barcodeStorageRepository.findByUserKakaoId(barcodeStorageServiceRequest.getKakaoId());
        return barcodesToGet.stream()
                .filter(BarcodeStorage::isUseYn)
                .toList();
    }

    @Override
    public BarcodeStorage addBarcode(BarcodeStorageServiceRequest barcodeStorageServiceRequest, BarcodeStorageRequest barcodeStorageRequest) {
        Optional<User> user = userRepository.findByKakaoId(barcodeStorageServiceRequest.getKakaoId());
        if (user.isPresent()) {
            User userEntity = user.get();
            BarcodeStorage barcodeToAdd = BarcodeStorage
                    .builder()
                    .barcodeContent(barcodeStorageRequest.getBarcodeContent())
                    .description(barcodeStorageRequest.getDescription())
                    .build();
            userEntity.addBarcodeStorage(barcodeToAdd);
            return barcodeStorageRepository.save(barcodeToAdd);
        } else {
            throw new ServerNotFound("사용자를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public BarcodeStorage deleteBarcode(BarcodeStorageServiceRequest barcodeStorageServiceRequest, Long barcodeStorageId) {
        BarcodeStorage barcodeToDelete = barcodeStorageRepository.findByUserKakaoIdAndId(barcodeStorageServiceRequest.getKakaoId(), barcodeStorageId)
                .orElseThrow(() -> new ServerNotFound("해당 바코드를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));
        barcodeToDelete.updateUseYn();
        return barcodeToDelete;
    }

    @Override
    public BarcodeStorage updateBarcode(BarcodeStorageServiceRequest barcodeStorageServiceRequest, Long barcodeStorageId, BarcodeStorageRequest barcodeStorageRequest) {
        BarcodeStorage barcodeToUpdate = barcodeStorageRepository.findByUserKakaoIdAndId(barcodeStorageServiceRequest.getKakaoId(), barcodeStorageId)
                .orElseThrow(() -> new ServerNotFound("해당 바코드를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));
        barcodeToUpdate.updateBarcodeInfo(barcodeStorageRequest);
        return barcodeToUpdate;

    }
}
