package goorm.mybatisboard.post.dto;

import goorm.mybatisboard.common.dto.BaseSearchConditionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CategorySearchConditionDto extends BaseSearchConditionDto {
  // Category 고유 필드들
  private Boolean isActive;
  private Integer minDisplayOrder;
  private Integer maxDisplayOrder;

  // Category 고유 유틸리티 메서드들
  public boolean hasActiveFilter() { return isActive != null; }
  public boolean hasDisplayOrderFilter() { return minDisplayOrder != null || maxDisplayOrder != null; }

  @Override
  public boolean isEmpty() {
    return !hasKeyword() && !hasActiveFilter() && !hasDisplayOrderFilter();
  }
}