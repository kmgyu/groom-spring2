-- MariaDB용 사용자 테이블 생성 (기존 JPA 엔티티와 호환)
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 인덱스 생성
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_nickname ON users(nickname);

-- 제약조건 추가
ALTER TABLE users ADD CONSTRAINT chk_users_email_format
    CHECK (email LIKE '%@%.%');

ALTER TABLE users ADD CONSTRAINT chk_users_nickname_length
    CHECK (LENGTH(nickname) >= 2 AND LENGTH(nickname) <= 20);