package goorm.mybatisboard.stock.category;

import goorm.mybatisboard.stock.category.dto.CategorySearchDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  Integer findMaxSortOrder();
  List<Category> findAllBySearchCondition(String keyword, boolean condition);
  List<Category> findAllActiveOrSelected(CategorySearchDto searchDto);
  List<Category> findByIsActiveOrderBySortOrderAsc(boolean isActive);
}
