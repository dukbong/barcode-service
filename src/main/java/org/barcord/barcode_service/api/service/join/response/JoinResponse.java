package org.barcord.barcode_service.api.service.join.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.barcord.barcode_service.domain.user.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class JoinResponse {

    private String username;
    private LocalDateTime createAt;

    @Builder
    public JoinResponse(String username, LocalDateTime createAt) {
        this.username = username;
        this.createAt = createAt;
    }

    public static JoinResponse createJoinResponse(User user) {
        return JoinResponse.builder()
                .username(user.getUsername())
                .createAt(user.getCreatedAt())
                .build();
    }

}
