-- 데이터베이스 테이블 삭제 스크립트
-- 외래키 관계를 고려한 역순 삭제

-- 1. 외래키 참조가 있는 테이블부터 삭제 (자식 → 부모 순서)
DROP TABLE IF EXISTS inventory_logs;    -- products.id, users.id 참조
DROP TABLE IF EXISTS order_items;       -- orders.id, products.id 참조
DROP TABLE IF EXISTS orders;            -- users.id 참조
DROP TABLE IF EXISTS posts;             -- users.id 참조

-- 2. 독립 테이블 삭제
DROP TABLE IF EXISTS products;          -- 독립 테이블

-- 3. 기본 테이블 삭제
DROP TABLE IF EXISTS users;             -- 기본 테이블 (다른 테이블들이 참조)

-- 4. Flyway 메타데이터 테이블 삭제 (선택사항)
-- DROP TABLE IF EXISTS flyway_schema_history;

-- 실행 후 확인
-- SHOW TABLES;