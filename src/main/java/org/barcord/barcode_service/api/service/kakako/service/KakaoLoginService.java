package org.barcord.barcode_service.api.service.kakako.service;

import org.barcord.barcode_service.api.service.kakako.response.KakaoLoginResponse;

public interface KakaoLoginService {
    String getRequestLocation();

    KakaoLoginResponse getAccessToken(String code);
}
