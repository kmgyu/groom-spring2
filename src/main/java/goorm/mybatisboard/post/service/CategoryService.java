package goorm.mybatisboard.post.service;

import goorm.mybatisboard.post.dto.CategoryDto;
import goorm.mybatisboard.post.dto.PageDto;
import goorm.mybatisboard.post.dto.SearchConditionDto;
import goorm.mybatisboard.post.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

  private final CategoryMapper categoryMapper;

  /**
   * 통합 검색 - 핵심 메서드 (PostService와 동일한 패턴)
   */
  public PageDto<CategoryDto> findAllWithConditions(SearchConditionDto condition) {
    List<CategoryDto> categories = categoryMapper.findAllWithConditions(condition);
    int totalCount = categoryMapper.countAllWithConditions(condition);

    return PageDto.of(categories, condition.getPage(), condition.getSize(), totalCount);
  }

  /**
   * 기본 목록 조회 (페이징)
   */
  public PageDto<CategoryDto> findAll(int page, int size) {
    int offset = Math.max(0, (page - 1) * size);
    List<CategoryDto> categories = categoryMapper.findAll(offset, size);
    int totalCount = categoryMapper.countAll();

    return PageDto.of(categories, page, size, totalCount);
  }

  /**
   * 활성 카테고리 목록 조회 (게시글 작성용)
   */
  public List<CategoryDto> getActiveCategories() {
    return categoryMapper.findActiveCategories();
  }


  // ========== 편의 메서드 ==========

  /**
   * 활성 상태별 카테고리 조회
   */
  public PageDto<CategoryDto> findByActiveStatus(boolean isActive, int page, int size) {
    SearchConditionDto condition = SearchConditionDto.builder()
            .isActive(isActive)
            .page(page)
            .size(size)
            .build();

    return findAllWithConditions(condition);
  }

  /**
   * 카테고리명 검색
   */
  public PageDto<CategoryDto> searchByName(String keyword, int page, int size) {
    SearchConditionDto condition = SearchConditionDto.builder()
            .keyword(keyword)
            .searchType("name")
            .page(page)
            .size(size)
            .build();

    return findAllWithConditions(condition);
  }
}