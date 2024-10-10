package org.barcord.barcode_service.domain.barcodestorage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BarcodeStorageRepository extends JpaRepository<BarcodeStorage, Long> {

    /***
     * SELECT *
     * FROM BARCODESTORAGE A
     * LEFT JOIN USER B ON (A.USER_ID = B.USER_ID)
     * WHERE B.KAKAO_ID = ?
     * @param userKakaoId
     * @return
     * :: 서브 쿼리로 연산시 결과가 많을 수 있기 때문에 LEFT JOIN을 사용
     */
    @Query("SELECT a from BarcodeStorage a LEFT JOIN a.user b where b.kakaoId = :userKakaoId")
    List<BarcodeStorage> findByUserKakaoId(String userKakaoId);

    /***
     * SELECT *
     * FROM BARCODESTORAGE A
     * WHERE A.USER_ID IN ( SELECT ID FROM USER B WHERE B.KAKAKO_ID = ? )
     * AND A.ID = ?
     * @param kakaoId
     * @param barcodeStorageId
     * @return
     * :: 서브 쿼리의 결과는 무조건 1개 이기 때문에 사용
     */
    Optional<BarcodeStorage> findByUserKakaoIdAndId(String kakaoId, Long barcodeStorageId);
}
