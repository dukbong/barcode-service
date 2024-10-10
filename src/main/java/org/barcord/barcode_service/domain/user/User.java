package org.barcord.barcode_service.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.barcord.barcode_service.api.service.kakako.role.KakaoRole;
import org.barcord.barcode_service.domain.BaseEntity;
import org.barcord.barcode_service.domain.barcodestorage.BarcodeStorage;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "users-seq-gen", sequenceName = "users-seq", initialValue = 1, allocationSize = 50)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "users-seq-gen")
    private Long id;

    @Column(nullable = false, unique = true)
    private String kakaoId;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private KakaoRole role;

    @OneToMany(mappedBy = "user")
    private List<BarcodeStorage> barcodeStorageList = new ArrayList<>();

    @Builder
    public User(String kakaoId, String nickName, KakaoRole role) {
        this.kakaoId = kakaoId;
        this.nickName = nickName;
        this.role = role;
    }

    public void addBarcodeStorage(BarcodeStorage barcodeStorage) {
        this.barcodeStorageList.add(barcodeStorage);
        barcodeStorage.updateUser(this);
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

}
