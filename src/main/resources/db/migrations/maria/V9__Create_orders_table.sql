-- Create orders table (주문)
CREATE TABLE orders (
    order_seq BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    customer_seq BIGINT NOT NULL,
    order_date DATE NOT NULL,
    due_date DATE,
    status ENUM('PENDING', 'CONFIRMED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    subtotal DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    tax_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    discount_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    shipping_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    total_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    payment_method VARCHAR(50),
    payment_status ENUM('PENDING', 'PAID', 'PARTIAL', 'OVERDUE', 'CANCELLED') DEFAULT 'PENDING',
    shipping_address TEXT,
    tracking_number VARCHAR(100),
    note TEXT,
    user_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_orders_number (order_number),
    INDEX idx_orders_customer_seq (customer_seq),
    INDEX idx_orders_date (order_date),
    INDEX idx_orders_status (status),
    INDEX idx_orders_payment_status (payment_status),
    INDEX idx_orders_user_id (user_id),
    INDEX idx_orders_created_at (created_at),

    CONSTRAINT fk_orders_customer_seq
        FOREIGN KEY (customer_seq)
        REFERENCES customers(customer_seq)
        ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT fk_orders_user_seq
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;