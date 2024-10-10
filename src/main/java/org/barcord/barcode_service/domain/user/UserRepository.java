package org.barcord.barcode_service.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	/***
	 * SELECT *
	 * FROM USER A
	 * WHERE A.KAKAO_ID = ?
	 * @param kakaoId
	 * @return
	 */
	Optional<User> findByKakaoId(String kakaoId);

}
