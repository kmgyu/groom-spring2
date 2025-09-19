package goorm.mybatisboard.stock.product;

import goorm.mybatisboard.stock.product.dto.ProductCreateDto;
import goorm.mybatisboard.stock.product.dto.ProductDto;
import goorm.mybatisboard.stock.product.dto.ProductSearchDto;
import goorm.mybatisboard.stock.product.dto.ProductUpdateDto;
import org.springframework.data.domain.Page;
import java.util.List;

/**
 * 상품 서비스 인터페이스
 */
public interface ProductService {

    /**
     * 상품 등록
     */
    ProductDto create(ProductCreateDto createDto);

    /**
     * 상품 수정
     */
    ProductDto update(ProductUpdateDto updateDto);

    /**
     * 상품 삭제
     */
    void delete(Long productSeq);

    /**
     * 상품 단건 조회
     */
    ProductDto findById(Long productSeq);

    /**
     * 상품 목록 조회 (페이징)
     */
    Page<ProductDto> findAll(ProductSearchDto searchDto);

    /**
     * 상품 코드 중복 확인
     */
    boolean isCodeDuplicate(String code);

    /**
     * 상품 코드 중복 확인 (수정 시)
     */
    boolean isCodeDuplicate(String code, Long excludeProductSeq);

    /**
     * 상태별 상품 목록 조회
     */
    List<ProductDto> findByStatus(String status);

    /**
     * 판매 가능한 상품 목록 조회
     */
    List<ProductDto> findSellableProducts();

    /**
     * 최근 등록된 상품 목록 조회
     */
    List<ProductDto> findRecentProducts(int limit);

    /**
     * 카테고리별 상품 개수
     */
    int countByCategory(Long categorySeq);

    /**
     * 공급업체별 상품 개수
     */
    int countBySupplier(Long supplierSeq);

    /**
     * Excel 내보내기용 상품 목록 조회 (페이징 없음)
     */
    List<ProductDto> findAllForExport(ProductSearchDto searchDto);

    /**
     * 상품 목록 Excel 내보내기
     */
    byte[] exportToExcel(ProductSearchDto searchDto);
}