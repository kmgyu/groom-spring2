-- MariaDB용 주문 상품 테이블 생성
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    total_price DECIMAL(12,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 외래키 설정
ALTER TABLE order_items ADD CONSTRAINT fk_order_items_order_id
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE;

ALTER TABLE order_items ADD CONSTRAINT fk_order_items_product_id
    FOREIGN KEY (product_id) REFERENCES products(id);

-- 인덱스 생성
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_product_id ON order_items(product_id);

-- 수량 및 가격 제약조건
ALTER TABLE order_items ADD CONSTRAINT chk_quantity
    CHECK (quantity > 0);

ALTER TABLE order_items ADD CONSTRAINT chk_unit_price
    CHECK (unit_price >= 0);

ALTER TABLE order_items ADD CONSTRAINT chk_total_price
    CHECK (total_price >= 0);

-- 총 가격 계산 일치성 검증 (unit_price * quantity = total_price)
ALTER TABLE order_items ADD CONSTRAINT chk_price_calculation
    CHECK (ABS(unit_price * quantity - total_price) < 0.01);

-- 복합 유니크 제약 (한 주문에서 같은 상품은 하나의 항목으로만)
ALTER TABLE order_items ADD CONSTRAINT uk_order_product
    UNIQUE (order_id, product_id);