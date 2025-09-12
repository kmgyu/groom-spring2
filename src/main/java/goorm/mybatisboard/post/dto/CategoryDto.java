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
public class CategoryDto {
  private Long id;
  private String name;
  private String description;
  private Integer displayOrder;
  private Boolean isActive;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // UI 표시용 메서드
  public String getDisplayName() {
    return name + (Boolean.FALSE.equals(isActive) ? " (비활성)" : "");
  }
}
