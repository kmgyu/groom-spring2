package goorm.mybatisboard.post.service;

import goorm.mybatisboard.post.mapper.PostMapper;
import goorm.mybatisboard.post.dto.*;
import goorm.mybatisboard.post.model.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("postServiceMyBatis")
@Profile("mybatis")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MyBatisPostServiceImpl implements PostService{
    
    private final PostMapper postMapper;

    public PageDto<PostListDto> findAll(int page, int size, String searchType, String keyword) {
        log.debug("Finding posts with search options: page={}, size={}, searchType={}, keyword={}", page, size, searchType, keyword);

        // data count search
        Integer totalElements = postMapper.countAllWithSearch(searchType, keyword);
        log.debug("Total posts count: {}", totalElements);


        // OFFSET 계산 (페이지는 1부터 시작)
        int offset = (page - 1) * size;

        // 페이징된 데이터 조회
        List<Post> posts = postMapper.findAllWithSearch(offset, size, searchType, keyword);
        log.debug("Found {} posts for page {}", posts.size(), page);

        List<PostListDto> postListDtos = posts.stream()
                .map(this::convertToListDto)
                .collect(Collectors.toList());

        return PageDto.of(postListDtos, page, size, totalElements);
    }

    public PageDto<PostListDto> findAll(int page, int size) {
        log.debug("Finding posts with pagination: page={}, size={}", page, size);

        // 전체 데이터 수 조회
        Integer totalElements = postMapper.countAll();
        log.debug("Total posts count: {}", totalElements);

        // OFFSET 계산 (페이지는 1부터 시작)
        int offset = (page - 1) * size;

        // 페이징된 데이터 조회
        List<Post> posts = postMapper.findAllWithPage(offset, size);
        log.debug("Found {} posts for page {}", posts.size(), page);

        List<PostListDto> postListDtos = posts.stream()
                .map(this::convertToListDto)
                .collect(Collectors.toList());

        return PageDto.of(postListDtos, page, size, totalElements);
    }

    public List<PostListDto> findAll() {
        // 전체 데이터 조회. 기본적으로 page 검색 기능을 사용한다.
        log.debug("Finding all posts");
        List<Post> posts = postMapper.findAll();
        log.debug("Found {} posts", posts.size());
        return posts.stream()
                .map(this::convertToListDto)
                .collect(Collectors.toList());
    }
    
    public PostDetailDto findBySeq(Long seq) {
        log.debug("Finding post by seq: {}", seq);
        Optional<Post> post = postMapper.findById(seq);
        if (post.isEmpty()) {
            log.error("Post not found with seq: {}", seq);
            throw new RuntimeException("게시글을 찾을 수 없습니다. ID: " + seq);
        }
        log.debug("Found post: {} (seq: {})", post.get().getTitle(), seq);
        return convertToDetailDto(post.get());
    }
    
    @Transactional
    public Post save(PostFormDto postFormDto) {
        log.debug("Saving new post with title: {}", postFormDto.getTitle());
        Post post = new Post();
        post.setTitle(postFormDto.getTitle());
        post.setContent(postFormDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        
        postMapper.save(post);
        log.info("Post saved successfully with title: {}", postFormDto.getTitle());
        return post;
    }
    
    @Transactional
    public Post update(Long seq, PostFormDto postFormDto) {
        log.debug("Updating post seq: {} with title: {}", seq, postFormDto.getTitle());
        Optional<Post> existingPost = postMapper.findById(seq);
        if (existingPost.isEmpty()) {
            log.error("Post not found for update with seq: {}", seq);
            throw new RuntimeException("게시글을 찾을 수 없습니다. ID: " + seq);
        }
        
        Post updatePost = new Post();
        updatePost.setTitle(postFormDto.getTitle());
        updatePost.setContent(postFormDto.getContent());
        updatePost.setUpdatedAt(LocalDateTime.now());
        
        postMapper.update(seq, updatePost);
        log.info("Post updated successfully seq: {}, title: {}", seq, postFormDto.getTitle());
        return postMapper.findById(seq).get();
    }
    
    @Transactional
    public void delete(Long seq) {
        log.debug("Deleting post seq: {}", seq);
        Optional<Post> existingPost = postMapper.findById(seq);
        if (existingPost.isEmpty()) {
            log.error("Post not found for deletion with seq: {}", seq);
            throw new RuntimeException("게시글을 찾을 수 없습니다. ID: " + seq);
        }
        postMapper.delete(seq);
        log.info("Post deleted successfully seq: {}, title: {}", seq, existingPost.get().getTitle());
    }
    
    private PostListDto convertToListDto(Post post) {
        PostListDto dto = new PostListDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setCreatedAt(post.getCreatedAt());

        return dto;
    }
    
    private PostDetailDto convertToDetailDto(Post post) {
        PostDetailDto dto = new PostDetailDto();
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        return dto;
    }

    // ========== 통합 검색 시스템 ==========

    public PageDto<PostDetailDto> findAllWithConditions(PostSearchConditionDto condition) {
        log.debug("Finding posts with integrated search conditions: {}", condition.toString());

//        condition.validateAndCorrect();

        int totalElements = postMapper.countAllWithConditions(condition);
        log.debug("Total posts count with conditions: {}", totalElements);

        List<PostDetailDto> posts = postMapper.findAllWithConditions(condition);
        log.debug("Found {} posts for page {}", posts.size(), condition.getPage());

        return PageDto.of(posts, condition.getPage(), condition.getSize(), totalElements);
    }

    public List<CategoryDto> findAllCategories() {
        log.debug("Finding all categories");
        List<CategoryDto> categories = postMapper.findAllCategories();
        log.debug("Found {} categories", categories.size());
        return categories;
    }

    public List<CategoryDto> findActiveCategories() {
        log.debug("Finding active categories");
        List<CategoryDto> categories = postMapper.findActiveCategories();
        log.debug("Found {} active categories", categories.size());
        return categories;
    }
}