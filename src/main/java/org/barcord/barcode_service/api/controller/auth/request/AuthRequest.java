package org.barcord.barcode_service.api.controller.auth.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthRequest {

    @NotEmpty(message = "사용자 이름은 필수입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    @Builder
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}