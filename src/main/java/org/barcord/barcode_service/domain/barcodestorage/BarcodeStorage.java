package org.barcord.barcode_service.domain.barcodestorage;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.barcord.barcode_service.api.controller.barcodestorage.request.BarcodeStorageRequest;
import org.barcord.barcode_service.domain.BaseEntity;
import org.barcord.barcode_service.domain.user.User;

@Entity
@Getter
@Table(name = "barcode_storage")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "barcode_storage_seq_gen", sequenceName = "barcode_storage_seq", initialValue = 1, allocationSize = 50)
public class BarcodeStorage extends BaseEntity {

    @Id
    @GeneratedValue(generator = "barcode_storage_seq_gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kakao_id")
    private User user;

    @Column(name = "barcode_content", nullable = false, length = 20)
    private String barcodeContent;

    @Column(nullable = true, length = 200)
    private String description;

    @Column(nullable = false)
    private boolean useYn;

    @Builder
    public BarcodeStorage(String barcodeContent, String description) {
        this.barcodeContent = barcodeContent;
        this.description = description;
        this.useYn = true;
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateUseYn() {
        this.useYn = !this.useYn;
    }

    public void updateBarcodeInfo(BarcodeStorageRequest barcodeStorageRequest) {
        this.barcodeContent = barcodeStorageRequest.getBarcodeContent();
        this.description = barcodeStorageRequest.getDescription();
    }
}
