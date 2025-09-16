package goorm.mybatisboard.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseSearchConditionDto {
  // 공통 필드들
  protected String keyword;
  protected String searchType = "all";
  protected int page = 1;
  protected int size = 10;
  protected String sortBy = "created_at";
  protected String sortDirection = "DESC";

  // 공통 유틸리티 메서드
  public int getOffset() { return Math.max(0, (page - 1) * size); }
  public boolean hasKeyword() { return keyword != null && !keyword.trim().isEmpty(); }
  public boolean isDefaultSort() { return "created_at".equals(sortBy) && "DESC".equals(sortDirection); }

  // 각 하위 클래스에서 구현
  public abstract boolean isEmpty();
}
