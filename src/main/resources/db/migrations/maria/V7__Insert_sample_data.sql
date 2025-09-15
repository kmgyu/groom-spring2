-- MariaDB용 사용자 샘플 데이터

-- 사용자 데이터 삽입 (비밀번호: password123, BCrypt 해시)
INSERT INTO users (email, password, nickname, created_at, updated_at) VALUES
('admin@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '관리자', NOW(), NOW()),
('user1@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '사용자1', NOW(), NOW()),
('user2@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4ioKoEa3Ro9llC/.og/at2uheWG/igi.', '사용자2', NOW(), NOW()),
('test@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', '테스트유저', NOW(), NOW());