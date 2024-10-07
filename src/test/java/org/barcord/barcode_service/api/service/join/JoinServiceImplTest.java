package org.barcord.barcode_service.api.service.join;

import org.barcord.barcode_service.exception.DuplicationUsername;
import org.barcord.barcode_service.api.service.join.request.JoinServiceRequest;
import org.barcord.barcode_service.api.service.join.response.JoinResponse;
import org.barcord.barcode_service.domain.user.User;
import org.barcord.barcode_service.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JoinServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JoinServiceImpl joinService;

    @Test
    void joinSuccess() {
        // given
        JoinServiceRequest joinServiceRequest = JoinServiceRequest.builder()
                .username("test1")
                .password("test1!")
                .build();

        when(userRepository.findByUsername("test1")).thenReturn(Optional.empty());

        // when
        JoinResponse response = joinService.join(joinServiceRequest);

        // then
        assertThat("test1").isEqualTo(response.getUsername());
    }

    @Test
    void joinDuplicationUsernameException() {
        // given
        JoinServiceRequest joinServiceRequest = JoinServiceRequest.builder()
                .username("test1")
                .password("test1!")
                .build();

        User user = User.builder().username("test1").password("test1!").build();

        when(userRepository.findByUsername("test1")).thenReturn(Optional.of(user));

        // when
        DuplicationUsername exception = assertThrows(DuplicationUsername.class, () -> {
            joinService.join(joinServiceRequest);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("사용자 이름은 사용중입니다.");
    }
}