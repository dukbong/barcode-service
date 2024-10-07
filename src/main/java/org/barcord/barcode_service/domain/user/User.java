package org.barcord.barcode_service.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.barcord.barcode_service.domain.barcodestorage.BarcodeStorage;
import org.barcord.barcode_service.domain.BaseEntity;

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

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false, length = 30)
    private String password;

    private String role;

    @OneToMany(mappedBy = "user")
    private List<BarcodeStorage> barcodeStorageList = new ArrayList<>();

    @Builder
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void addBarcodeStorage(BarcodeStorage barcodeStorage) {
        this.barcodeStorageList.add(barcodeStorage);
        barcodeStorage.updateUser(this);
    }

}
