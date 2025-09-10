package goorm.mybatisboard.post.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageDto<T> {

    private List<T> content;
    private int currentPage;
    private int size;
    private long totalElements;
    private int totalPages;

    // 계산된 속성들
    public boolean hasNext() {
        return currentPage < totalPages;
    }

    public boolean hasPrevious() {
        return currentPage > 1;
    }

    public boolean isFirst() {
        return currentPage == 1;
    }

    public boolean isLast() {
        return currentPage == totalPages;
    }

    public int getStartPage() {
        int start = Math.max(1, currentPage - 2);
        return Math.min(start, Math.max(1, totalPages - 4));
    }

    public int getEndPage() {
        int end = Math.min(totalPages, currentPage + 2);
        return Math.max(end, Math.min(5, totalPages));
    }

    // 정적 팩토리 메서드
    public static <T> PageDto<T> of(List<T> content, int currentPage, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new PageDto<>(content, currentPage, size, totalElements, totalPages);
    }
//    has next, hasprevious, isfirst, islast, getstartpage, getendpage
}
