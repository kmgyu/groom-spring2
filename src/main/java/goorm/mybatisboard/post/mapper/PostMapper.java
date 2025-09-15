package goorm.mybatisboard.post.mapper;

import goorm.mybatisboard.post.dto.CategoryDto;
import goorm.mybatisboard.post.dto.PostDetailDto;
import goorm.mybatisboard.post.dto.PostSearchConditionDto;
import goorm.mybatisboard.post.dto.SearchConditionDto;
import goorm.mybatisboard.post.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {
    List<Post> findAllWithPage(@Param("offset") int offset, @Param("size") int size);

    List<Post> findAllWithSearch(@Param("offset") int offset,
                                 @Param("size") int size,
                                 @Param("searchType") String searchType,
                                 @Param("keyword") String keyword);

    List<Post> findAll();

    Integer countAll();

    Integer countAllWithSearch(@Param("searchType") String searchType,
                           @Param("keyword") String keyword);

    Optional<Post> findById(@Param("id") Long id);
    
    void save(Post post);
    
    void update(@Param("id") Long id, @Param("post") Post post);
    
    void delete(@Param("id") Long id);

    // ========== 통합 검색 시스템 ==========

    List<PostDetailDto> findAllWithConditions(PostSearchConditionDto condition);

    int countAllWithConditions(PostSearchConditionDto condition);

    // ========== 카테고리 관리 ==========

    List<CategoryDto> findAllCategories();

    List<CategoryDto> findActiveCategories();
}