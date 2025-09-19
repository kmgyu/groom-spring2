package goorm.mybatisboard.stock.supplier.dto;

import goorm.mybatisboard.stock.supplier.SupplierStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 공급업체 정보 전송용 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {

    private Long supplierSeq;

    private String name;

    private String contactPerson;

    private String email;

    private String phone;

    private String address;

    private String description;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long createdSeq;

    private Long updatedSeq;

    /**
     * Boolean isActive를 SupplierStatus로 변환
     */
    public SupplierStatus getStatus() {
        return Boolean.TRUE.equals(isActive) ? SupplierStatus.ACTIVE : SupplierStatus.INACTIVE;
    }

    /**
     * 상태 표시명 반환
     */
    public String getStatusDisplayName() {
        return getStatus().getDisplayName();
    }
}