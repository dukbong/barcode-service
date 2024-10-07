package org.barcord.barcode_service.api.service.join;

import lombok.RequiredArgsConstructor;
import org.barcord.barcode_service.exception.DuplicationUsername;
import org.barcord.barcode_service.api.service.join.request.JoinServiceRequest;
import org.barcord.barcode_service.api.service.join.response.JoinResponse;
import org.barcord.barcode_service.domain.user.User;
import org.barcord.barcode_service.domain.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public JoinResponse join(JoinServiceRequest joinServiceRequest) {

        Optional<User> findByUsername = userRepository.findByUsername(joinServiceRequest.getUsername());

        if (findByUsername.isPresent()) {
            throw new DuplicationUsername("사용자 이름은 사용중입니다.");
        }

        User newUser = userRepository.save(User.builder()
                .username(joinServiceRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(joinServiceRequest.getPassword()))
                .build());

        return JoinResponse.createJoinResponse(newUser);

    }

}
