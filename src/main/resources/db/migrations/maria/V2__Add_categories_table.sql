-- 카테고리 테이블 생성 (이미 존재할 경우 무시)
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    display_order INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 기본 카테고리 데이터 삽입 (중복 방지)
INSERT IGNORE INTO categories (id, name, description, display_order, is_active) VALUES
(1, '공지사항', '중요한 공지사항을 위한 카테고리', 1, TRUE),
(2, '일반', '일반적인 게시글을 위한 카테고리', 2, TRUE),
(3, '질문', '질문과 답변을 위한 카테고리', 3, TRUE),
(4, '정보공유', '유용한 정보 공유를 위한 카테고리', 4, TRUE),
(5, '자유게시판', '자유로운 소통을 위한 카테고리', 5, TRUE);