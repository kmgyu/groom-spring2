package goorm.mybatisboard.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCategoryDetailDto {
  private Long id;
  private String title;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private int viewCount;
  private boolean isNotice;

  private String status;
  private String authorName;
  private Long categoryId;
  private String categoryName;
}
