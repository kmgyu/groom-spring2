-- MariaDB용 상품 테이블 생성
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(12,2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    min_threshold INT DEFAULT 10,
    max_threshold INT DEFAULT 1000,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    category VARCHAR(50) NOT NULL DEFAULT 'ELECTRONICS',
    image_url VARCHAR(255),
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 인덱스 생성
CREATE INDEX idx_products_category_status ON products(category, status);
CREATE INDEX idx_products_stock_quantity ON products(stock_quantity);
CREATE INDEX idx_products_product_code ON products(product_code);

-- 상품 상태 및 카테고리 체크 제약조건
ALTER TABLE products ADD CONSTRAINT chk_product_status
    CHECK (status IN ('ACTIVE', 'INACTIVE', 'OUT_OF_STOCK'));

ALTER TABLE products ADD CONSTRAINT chk_product_category
    CHECK (category IN ('ELECTRONICS', 'CLOTHING', 'FOOD', 'BOOKS'));

ALTER TABLE products ADD CONSTRAINT chk_product_price
    CHECK (price > 0);

ALTER TABLE products ADD CONSTRAINT chk_stock_quantity
    CHECK (stock_quantity >= 0);

ALTER TABLE products ADD CONSTRAINT chk_threshold_values
    CHECK (min_threshold >= 0 AND max_threshold >= min_threshold);

-- 상품 코드 패턴 검증 (P + 숫자)
ALTER TABLE products ADD CONSTRAINT chk_product_code_pattern
    CHECK (product_code REGEXP '^P[0-9]+$');