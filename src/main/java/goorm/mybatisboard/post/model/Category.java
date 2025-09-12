package goorm.mybatisboard.post.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  private Long id;
  private String name;
  private String description;
  private Integer displayOrder;
  private Boolean isActive;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}