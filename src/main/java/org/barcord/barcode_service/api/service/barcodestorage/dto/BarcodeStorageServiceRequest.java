package org.barcord.barcode_service.api.service.barcodestorage.dto;

import lombok.Builder;
import lombok.Getter;
import org.barcord.barcode_service.api.service.barcodestorage.BarcodeStorageService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Getter
@Builder
public class BarcodeStorageServiceRequest {
    private String kakaoId;
    private String nickname;

    public static BarcodeStorageServiceRequest create(OAuth2User oAuth2User) {
        return BarcodeStorageServiceRequest.builder()
                .kakaoId(oAuth2User.getName())
                .nickname(((Map<String, Object>) oAuth2User.getAttributes().get("properties")).get("nickname").toString())
                .build();
    }
}
