package goorm.mybatisboard.post.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDetailDto {
    
    private String title;
    private String content;
    private LocalDateTime createdAt;
}