package org.barcord.barcode_service.api.service.kakako.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("expires_in")
    private Integer expiresIn;
    @JsonProperty("app_id")
    private Integer appId;

}
