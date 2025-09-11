package goorm.mybatisboard.post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    Post findById(@Param("id") Long id);
    
    void save(Post post);
    
    void update(@Param("id") Long id, @Param("post") Post post);
    
    void delete(@Param("id") Long id);
}