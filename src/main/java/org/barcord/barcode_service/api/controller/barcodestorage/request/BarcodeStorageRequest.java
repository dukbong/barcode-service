package org.barcord.barcode_service.api.controller.barcodestorage.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BarcodeStorageRequest {

    @NotEmpty(message = "바코드 내용은 필수입니다.")
    private String barcodeContent;

    private String description;

    @Builder
    public BarcodeStorageRequest(String barcodeContent, String description) {
        this.barcodeContent = barcodeContent;
        this.description = description;
    }
}
