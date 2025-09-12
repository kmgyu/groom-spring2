package goorm.mybatisboard.post.dto;

import goorm.mybatisboard.common.dto.BaseSearchConditionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PostSearchConditionDto extends BaseSearchConditionDto {
  // Post 고유 필드들
  private List<Long> categoryIds;
  private String status;
  private String authorName;
  private Boolean isNotice;
  private LocalDate startDate;
  private LocalDate endDate;

  // Post 고유 유틸리티 메서드들
  public boolean hasCategoryFilter() { return categoryIds != null && !categoryIds.isEmpty(); }
  public boolean hasStatusFilter() { return status != null && !status.trim().isEmpty(); }
  // ... 기타 Post 고유 메서드들

  @Override
  public boolean isEmpty() {
    return !hasKeyword() && !hasCategoryFilter() && !hasStatusFilter();
//            && !hasAuthorFilter() && isNotice == null && !hasDateRangeFilter();
  }
}

