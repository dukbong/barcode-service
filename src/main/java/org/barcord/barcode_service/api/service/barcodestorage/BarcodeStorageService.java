package org.barcord.barcode_service.api.service.barcodestorage;

import org.barcord.barcode_service.api.controller.barcodestorage.request.BarcodeStorageRequest;
import org.barcord.barcode_service.api.service.barcodestorage.dto.BarcodeStorageServiceRequest;
import org.barcord.barcode_service.domain.barcodestorage.BarcodeStorage;

import java.util.List;

public interface BarcodeStorageService {
    List<BarcodeStorage> getBarcodesByUser(BarcodeStorageServiceRequest barcodeStorageServiceRequest);

    BarcodeStorage addBarcode(BarcodeStorageServiceRequest barcodeStorageServiceRequest, BarcodeStorageRequest barcodeStorageRequest);

    BarcodeStorage deleteBarcode(BarcodeStorageServiceRequest barcodeStorageServiceRequest, Long barcodeStorageId);

    BarcodeStorage updateBarcode(BarcodeStorageServiceRequest barcodeStorageServiceRequest, Long barcodeStorageId, BarcodeStorageRequest barcodeStorageRequest);
}
