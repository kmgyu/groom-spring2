-- MariaDB용 주문 테이블 생성
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(50) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    customer_phone VARCHAR(20) NOT NULL,
    total_amount DECIMAL(12,2) NOT NULL,
    discount_amount DECIMAL(12,2) DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    tracking_number VARCHAR(100),
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 외래키 설정
ALTER TABLE orders ADD CONSTRAINT fk_orders_user_id
    FOREIGN KEY (user_id) REFERENCES users(id);

-- 인덱스 생성
CREATE INDEX idx_orders_status_order_date ON orders(status, order_date);
CREATE INDEX idx_orders_customer_email ON orders(customer_email);
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_order_number ON orders(order_number);

-- 주문 상태 제약조건
ALTER TABLE orders ADD CONSTRAINT chk_order_status
    CHECK (status IN ('PENDING', 'CONFIRMED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED'));

-- 금액 제약조건
ALTER TABLE orders ADD CONSTRAINT chk_total_amount
    CHECK (total_amount >= 0);

ALTER TABLE orders ADD CONSTRAINT chk_discount_amount
    CHECK (discount_amount >= 0 AND discount_amount <= total_amount);

-- 이메일 형식 검증
ALTER TABLE orders ADD CONSTRAINT chk_email_format
    CHECK (customer_email LIKE '%@%.%');

-- 주문번호 패턴 검증 (ORD- + 날짜 + 숫자)
ALTER TABLE orders ADD CONSTRAINT chk_order_number_pattern
    CHECK (order_number REGEXP '^ORD-[0-9]{8}-[0-9]+$');