package goorm.mybatisboard.post.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * summary of PostDTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostListDto {
    
    private Long id;
    private String title;
    private LocalDateTime createdAt;

    private boolean isNotice;
    private String authorName;
    private Long categoryId;
    private String categoryName;
    private int viewCount;
}