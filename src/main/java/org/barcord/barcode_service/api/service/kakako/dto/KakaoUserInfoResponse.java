package org.barcord.barcode_service.api.service.kakako.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponse {

    @JsonProperty("kakao_account.profile")
    private String profile;
    @JsonProperty("kakao_account.name")
    private String name;
    @JsonProperty("kakao_account.email")
    private String email;
    @JsonProperty("kakao_account.age_range")
    private String age_range;
    @JsonProperty("kakao_account.birthday")
    private String gender;
    @JsonProperty("kakao_account.gender")
    private String birthday;

}
