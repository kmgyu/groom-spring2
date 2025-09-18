package goorm.mybatisboard.product.dto;

import lombok.*;

/**
 * 공급업체 DTO
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
}