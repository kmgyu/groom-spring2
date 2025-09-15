package goorm.mybatisboard.post.service;

import goorm.mybatisboard.post.dto.*;
import goorm.mybatisboard.post.model.Post;

import java.util.List;

public interface PostService {
//  public PageDto<PostListDto> findAll(int page, int size, String searchType, String keyword);
//  public PageDto<PostListDto> findAll(int page, int size);
//  public List<PostListDto> findAll();
  public PostDetailDto findBySeq(Long seq);
  public Post save(PostFormDto postFormDto);
  public Post update(Long seq, PostFormDto postFormDto);
  public void delete(Long seq);

  public PageDto<PostDetailDto> findAllWithConditions(PostSearchConditionDto condition);
  public List<CategoryDto> findAllCategories();
  public List<CategoryDto> findActiveCategories();
}
