package goorm.mybatisboard.post.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostFormDto {
    
    private String title;
    private String content;
}