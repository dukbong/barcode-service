package org.barcord.barcode_service.api.controller.barcodestorage;

import lombok.RequiredArgsConstructor;
import org.barcord.barcode_service.api.controller.barcodestorage.request.BarcodeStorageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/barcode_storage")
public class BarcodeStorageController {

    // 바코드 등록하기
    @PostMapping
    public ResponseEntity<String> addBarcode(@RequestBody BarcodeStorageRequest barcodeStorageRequest) {
        return null;
    }

    // 바코드 가져오기 (특정 사용자 바코드 목록)
    @GetMapping
    public ResponseEntity<List<String>> getBarcodesByUser() {
        return null;
    }

    // 바코드 수정하기
    @PutMapping("/{barcodeStorageId}")
    public ResponseEntity<String> updateBarcode(@PathVariable Long barcodeStorageId,
                                                @RequestBody BarcodeStorageRequest barcodeStorageRequest) {
        return null;
    }

    // 바코드 삭제하기
    @DeleteMapping("/{barcodeStorageId}")
    public ResponseEntity<Void> deleteBarcode(@PathVariable Long barcodeStorageId) {
        return null;
    }
}
