# 데이터베이스 마이그레이션 가이드

## 마이그레이션 실행 순서

Flyway는 파일명 순서대로 마이그레이션을 실행합니다:

### 1. 테이블 생성 (DDL)
- `V1__Create_users_table.sql` - 사용자 테이블 생성
- `V2__Create_posts_table.sql` - 게시글 테이블 생성 (users 외래키 참조)
- `V0_2__Add_updated_at_to_posts.sql` - posts 테이블에 updated_at 컬럼 추가
- `V3__Create_products_table.sql` - 상품 테이블 생성
- `V4__Create_orders_table.sql` - 주문 테이블 생성 (users 외래키 참조)
- `V5__Create_order_items_table.sql` - 주문상품 테이블 생성 (orders, products 외래키 참조)
- `V6__Create_inventory_logs_table.sql` - 재고변동로그 테이블 생성 (products, users 외래키 참조)

### 2. 샘플 데이터 삽입 (DML)
- `V7__Insert_sample_data.sql` - 사용자 샘플 데이터 삽입
- `V8__Insert_basic_sample_data.sql` - 게시글 샘플 데이터 삽입

## 테이블 의존성 관계

```
users (기본)
  ├── posts (user_id → users.id)
  ├── orders (user_id → users.id)
  └── inventory_logs (created_by → users.id)

products (독립)
  ├── order_items (product_id → products.id)
  └── inventory_logs (product_id → products.id)

orders (users 의존)
  └── order_items (order_id → orders.id)
```

## 테이블 삭제 순서 (역순)

외래키 관계 때문에 다음 순서로 삭제해야 합니다:

```sql
-- 1. 데이터 삭제 (외래키 참조가 있는 테이블부터)
DROP TABLE IF EXISTS inventory_logs;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS posts;

-- 2. 독립 테이블 삭제
DROP TABLE IF EXISTS products;

-- 3. 기본 테이블 삭제
DROP TABLE IF EXISTS users;

-- 4. Flyway 히스토리 삭제 (필요시)
DROP TABLE IF EXISTS flyway_schema_history;
```

## 개발 시 유용한 명령어

### 전체 테이블 초기화
```sql
-- 모든 테이블 삭제 (위 순서대로)
DROP TABLE IF EXISTS inventory_logs;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS flyway_schema_history;
```

### Flyway 명령어
```bash
# 마이그레이션 정보 확인
./gradlew flywayInfo

# 마이그레이션 실행
./gradlew flywayMigrate

# 데이터베이스 클린 (모든 객체 삭제)
./gradlew flywayClean

# 검증
./gradlew flywayValidate
```

## 주의사항

1. **외래키 제약조건**: 테이블 삭제 시 반드시 참조하는 테이블부터 삭제
2. **데이터 백업**: 운영 환경에서는 삭제 전 반드시 데이터 백업
3. **트랜잭션**: 여러 테이블 작업 시 트랜잭션 처리 권장
4. **Flyway Clean**: `clean-disabled: false` 설정이 있어야 사용 가능

## 샘플 데이터

### 사용자 (V5)
- admin@example.com (관리자)
- user1@example.com (사용자1)
- user2@example.com (사용자2)
- test@example.com (테스트유저)
- **공통 비밀번호**: password123

### 게시글 (V6)
- 7개의 기술 관련 게시글
- 각 사용자별로 작성된 샘플 게시글