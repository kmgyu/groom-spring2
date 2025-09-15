-- MariaDB용 게시글 테이블 생성 (기존 JPA 엔티티와 호환)
CREATE TABLE posts (
    seq BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    view_count BIGINT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 외래키 설정
ALTER TABLE posts ADD CONSTRAINT fk_posts_user_id
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

-- 인덱스 생성
CREATE INDEX idx_posts_user_id ON posts(user_id);
CREATE INDEX idx_posts_created_at ON posts(created_at);
CREATE INDEX idx_posts_title ON posts(title);

-- 제약조건 추가
ALTER TABLE posts ADD CONSTRAINT chk_posts_title_length
    CHECK (LENGTH(title) >= 1 AND LENGTH(title) <= 200);

ALTER TABLE posts ADD CONSTRAINT chk_posts_content_length
    CHECK (LENGTH(content) >= 10 AND LENGTH(content) <= 4000);

ALTER TABLE posts ADD CONSTRAINT chk_posts_view_count
    CHECK (view_count >= 0);