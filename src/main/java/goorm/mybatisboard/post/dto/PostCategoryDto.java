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
public class PostCategoryDto {
  private Long id;
  private String title;
  private String content;
  private String status;
  private String authorName;
  private Long categoryId;
  private String categoryName;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}