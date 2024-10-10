package org.barcord.barcode_service.api.controller.barcodestorage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.barcord.barcode_service.api.controller.barcodestorage.request.BarcodeStorageRequest;
import org.barcord.barcode_service.api.service.barcodestorage.BarcodeStorageService;
import org.barcord.barcode_service.api.service.barcodestorage.dto.BarcodeStorageServiceRequest;
import org.barcord.barcode_service.domain.barcodestorage.BarcodeStorage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/barcode_storage")
public class BarcodeStorageController {

    private final BarcodeStorageService barcodeStorageServiceImpl;

    // 바코드 등록하기
    @PostMapping
    public ResponseEntity<BarcodeStorage> addBarcode(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody BarcodeStorageRequest barcodeStorageRequest) {
        return ResponseEntity.ok().body(barcodeStorageServiceImpl.addBarcode(BarcodeStorageServiceRequest.create(oAuth2User), barcodeStorageRequest));
    }

    // 바코드 가져오기 (특정 사용자 바코드 목록)
    @GetMapping
    public ResponseEntity<List<BarcodeStorage>> getBarcodesByUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return ResponseEntity.ok().body(barcodeStorageServiceImpl.getBarcodesByUser(BarcodeStorageServiceRequest.create(oAuth2User)));
    }

    // 바코드 수정하기
    @PutMapping("/{barcodeStorageId}")
    public ResponseEntity<BarcodeStorage> updateBarcode(@AuthenticationPrincipal OAuth2User oAuth2User,
                                                        @PathVariable Long barcodeStorageId,
                                                        @RequestBody BarcodeStorageRequest barcodeStorageRequest) {
        return ResponseEntity.ok().body(barcodeStorageServiceImpl.updateBarcode(BarcodeStorageServiceRequest.create(oAuth2User), barcodeStorageId, barcodeStorageRequest));
    }

    // 바코드 삭제하기
    @DeleteMapping("/{barcodeStorageId}")
    public ResponseEntity<BarcodeStorage> deleteBarcode(@AuthenticationPrincipal OAuth2User oAuth2User, @PathVariable Long barcodeStorageId) {
        return ResponseEntity.ok().body(barcodeStorageServiceImpl.deleteBarcode(BarcodeStorageServiceRequest.create(oAuth2User), barcodeStorageId));
    }
}
