package goorm.mybatisboard.product.mapper;

import goorm.mybatisboard.product.dto.ProductDto;
import goorm.mybatisboard.product.dto.ProductSearchDto;
import goorm.mybatisboard.product.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Optional;

/**
 * 상품 관련 MyBatis Mapper 인터페이스
 */
@Mapper
public interface ProductMapper {

    /**
     * 상품 등록
     */
    int insert(Product product);

    /**
     * 상품 수정
     */
    int update(Product product);

    /**
     * 상품 삭제 (물리삭제)
     */
    int delete(@Param("productSeq") Long productSeq);

    /**
     * 상품 단건 조회 (Entity)
     */
    Optional<Product> findById(@Param("productSeq") Long productSeq);

    /**
     * 상품 코드로 조회 (Entity)
     */
    Optional<Product> findByCode(@Param("code") String code);

    /**
     * 상품 단건 조회 (DTO)
     */
    Optional<ProductDto> findDtoById(@Param("productSeq") Long productSeq);

    /**
     * 상품 목록 조회 (검색 조건 포함)
     */
    List<ProductDto> findAll(@Param("search") ProductSearchDto searchDto);

    /**
     * 상품 총 개수 (검색 조건 포함)
     */
    int count(@Param("search") ProductSearchDto searchDto);

    /**
     * 상품 코드 중복 확인
     */
    boolean existsByCode(@Param("code") String code);

    /**
     * 상품 코드 중복 확인 (수정 시, 자신 제외)
     */
    boolean existsByCodeAndNotSeq(@Param("code") String code, @Param("productSeq") Long productSeq);

    /**
     * 카테고리별 상품 개수
     */
    int countByCategory(@Param("categorySeq") Long categorySeq);

    /**
     * 공급업체별 상품 개수
     */
    int countBySupplier(@Param("supplierSeq") Long supplierSeq);

    /**
     * 상태별 상품 목록 조회
     */
    List<ProductDto> findByStatus(@Param("status") String status);

    /**
     * 판매 가능한 상품 목록 조회
     */
    List<ProductDto> findSellableProducts();

    /**
     * 최근 등록된 상품 목록 조회
     */
    List<ProductDto> findRecentProducts(@Param("limit") int limit);
}