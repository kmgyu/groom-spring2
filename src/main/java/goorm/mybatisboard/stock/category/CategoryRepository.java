package goorm.mybatisboard.stock.category;

import goorm.mybatisboard.stock.category.dto.CategorySearchDto;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("jpa")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  Integer findMaxSortOrder();
  List<Category> findAllBySearchCondition(String keyword, boolean condition);
  List<Category> findAllActiveOrSelected(CategorySearchDto searchDto);
  List<Category> findByIsActiveOrderBySortOrderAsc(boolean isActive);
}
