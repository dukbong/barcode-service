package org.barcord.barcode_service.api.service.join.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.barcord.barcode_service.api.controller.auth.request.AuthRequest;

@Getter
@NoArgsConstructor
public class JoinServiceRequest {

    private String username;
    private String password;

    @Builder
    public JoinServiceRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static JoinServiceRequest createJoinServiceRequest(AuthRequest authRequest) {
        return JoinServiceRequest.builder()
                .username(authRequest.getUsername())
                .password(authRequest.getPassword())
                .build();
    }
}
