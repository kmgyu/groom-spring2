-- MariaDB용 재고 변동 로그 테이블 생성
CREATE TABLE inventory_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    change_type VARCHAR(20) NOT NULL,
    quantity_change INT NOT NULL,
    before_quantity INT NOT NULL,
    after_quantity INT NOT NULL,
    reference_type VARCHAR(50),
    reference_id BIGINT,
    reason VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT
);

-- 외래키 설정
ALTER TABLE inventory_logs ADD CONSTRAINT fk_inventory_logs_product_id
    FOREIGN KEY (product_id) REFERENCES products(id);

ALTER TABLE inventory_logs ADD CONSTRAINT fk_inventory_logs_created_by
    FOREIGN KEY (created_by) REFERENCES users(id);

-- 인덱스 생성
CREATE INDEX idx_inventory_logs_product_created ON inventory_logs(product_id, created_at);
CREATE INDEX idx_inventory_logs_change_type ON inventory_logs(change_type);
CREATE INDEX idx_inventory_logs_reference ON inventory_logs(reference_type, reference_id);
CREATE INDEX idx_inventory_logs_created_by ON inventory_logs(created_by);

-- 변동 유형 제약조건
ALTER TABLE inventory_logs ADD CONSTRAINT chk_change_type
    CHECK (change_type IN ('PURCHASE', 'SALE', 'ADJUSTMENT', 'RETURN', 'DAMAGE', 'TRANSFER'));

-- 참조 유형 제약조건
ALTER TABLE inventory_logs ADD CONSTRAINT chk_reference_type
    CHECK (reference_type IN ('ORDER', 'ADJUSTMENT', 'RETURN', 'DAMAGE', 'TRANSFER', 'PURCHASE') OR reference_type IS NULL);

-- 수량 변동 로직 검증
ALTER TABLE inventory_logs ADD CONSTRAINT chk_quantity_logic
    CHECK (before_quantity + quantity_change = after_quantity);

-- 수량은 0 이상이어야 함
ALTER TABLE inventory_logs ADD CONSTRAINT chk_quantities_positive
    CHECK (before_quantity >= 0 AND after_quantity >= 0);

-- 참조 ID가 있으면 참조 타입도 있어야 함
ALTER TABLE inventory_logs ADD CONSTRAINT chk_reference_consistency
    CHECK ((reference_id IS NULL AND reference_type IS NULL) OR
           (reference_id IS NOT NULL AND reference_type IS NOT NULL));