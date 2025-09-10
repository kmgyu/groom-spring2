package goorm.mybatisboard.post.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostListDto {
    
    private Long id;
    private String title;
    private LocalDateTime createdAt;
}