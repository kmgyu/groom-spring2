package goorm.mybatisboard.stock.category.dto;

import goorm.mybatisboard.common.dto.BaseSearchConditionDto;
import goorm.mybatisboard.stock.category.CategoryStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 카테고리 검색용 DTO
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CategorySearchDto extends BaseSearchConditionDto {

    private CategoryStatus status;

    /**
     * 상태 필터 여부 확인
     */
    public boolean hasStatus() {
        return status != null;
    }

    @Override
    public boolean isEmpty() {
        return !hasKeyword() && status == null;
    }

    /**
     * 검색 조건 존재 여부 확인
     */
    public boolean hasSearchCondition() {
        return hasKeyword() || status != null;
    }

}