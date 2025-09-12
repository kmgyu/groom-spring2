-- posts 테이블에 컬럼 추가 (이미 존재할 경우 무시)
ALTER TABLE posts
    ADD COLUMN IF NOT EXISTS category_id BIGINT DEFAULT 2,
    ADD COLUMN IF NOT EXISTS status VARCHAR(20) DEFAULT 'PUBLISHED',
    ADD COLUMN IF NOT EXISTS author_name VARCHAR(100) DEFAULT '작성자',
    ADD COLUMN IF NOT EXISTS view_count INT DEFAULT 0,
    ADD COLUMN IF NOT EXISTS is_notice BOOLEAN DEFAULT FALSE;

-- 외래키 제약조건 추가 (이미 존재할 경우 무시)
SET @foreign_key_exists = (SELECT COUNT(*)
   FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
   WHERE CONSTRAINT_NAME = 'fk_posts_category'
     AND TABLE_NAME = 'posts'
     AND TABLE_SCHEMA = DATABASE());

SET @sql = IF(@foreign_key_exists = 0,
  'ALTER TABLE posts ADD CONSTRAINT fk_posts_category FOREIGN KEY (category_id) REFERENCES categories(id)',
  'SELECT "Foreign key already exists" as message');

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 인덱스 추가 (이미 존재할 경우 무시)
CREATE INDEX IF NOT EXISTS idx_posts_category_status ON posts(category_id, status);
CREATE INDEX IF NOT EXISTS idx_posts_created_at ON posts(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_posts_author ON posts(author_name);

-- 기존 데이터에 확장 필드가 기본값으로 되어있으면 업데이트 (테스트용)
UPDATE posts SET
    category_id = CASE
        WHEN category_id = 2 AND id % 5 = 1 THEN 1  -- 공지사항
        WHEN category_id = 2 AND id % 5 = 2 THEN 2  -- 일반
        WHEN category_id = 2 AND id % 5 = 3 THEN 3  -- 질문
        WHEN category_id = 2 AND id % 5 = 4 THEN 4  -- 정보공유
        WHEN category_id = 2 AND id % 5 = 0 THEN 5  -- 자유게시판
        ELSE category_id
     END,
    status = CASE
        WHEN status = 'PUBLISHED' AND RAND() > 0.9 THEN 'DRAFT'
        WHEN status = 'PUBLISHED' AND RAND() > 0.95 THEN 'DELETED'
        ELSE status
     END,
    author_name = CASE
        WHEN author_name = '작성자' THEN CONCAT('사용자', FLOOR(1 + RAND() * 20))
        ELSE author_name
     END,
    view_count = CASE
        WHEN view_count = 0 THEN FLOOR(RAND() * 100)
        ELSE view_count
     END,
    is_notice = CASE WHEN id % 10 = 1 THEN TRUE ELSE is_notice END
WHERE (category_id = 2 AND author_name = '작성자' AND view_count = 0);