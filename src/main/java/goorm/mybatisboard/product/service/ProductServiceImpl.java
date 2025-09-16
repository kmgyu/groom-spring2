package goorm.mybatisboard.product.service;

import goorm.mybatisboard.product.dto.ProductCreateDto;
import goorm.mybatisboard.product.dto.ProductDto;
import goorm.mybatisboard.product.dto.ProductSearchDto;
import goorm.mybatisboard.product.dto.ProductUpdateDto;
import goorm.mybatisboard.product.exception.ProductNotFoundException;
import goorm.mybatisboard.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
  private final ProductMapper productMapper;

  @Override
  public Page<ProductDto> findAll(ProductSearchDto searchDto) {
    List<ProductDto> products = productMapper.findAll(searchDto);
    int total = productMapper.count(searchDto);

    PageRequest pageRequest = PageRequest.of(
            Math.max(0, searchDto.getPage() - 1),
            searchDto.getSize()
    );

    return new PageImpl<>(products, pageRequest, total);
  }

  @Override
  public ProductDto create(ProductCreateDto createDto) {
    return null;
  }

  @Override
  public ProductDto update(ProductUpdateDto updateDto) {
    return null;
  }

  @Override
  public void delete(Long productSeq) {

  }

  @Override
  public ProductDto findById(Long productSeq) {
    return productMapper.findDtoById(productSeq)
            .orElseThrow(() -> new ProductNotFoundException(productSeq));
  }

  @Override
  public boolean isCodeDuplicate(String code) {
    return false;
  }

  @Override
  public boolean isCodeDuplicate(String code, Long excludeProductSeq) {
    return false;
  }

  @Override
  public List<ProductDto> findByStatus(String status) {
    return List.of();
  }

  @Override
  public List<ProductDto> findSellableProducts() {
    return List.of();
  }

  @Override
  public List<ProductDto> findRecentProducts(int limit) {
    return List.of();
  }

  @Override
  public int countByCategory(Long categorySeq) {
    return 0;
  }

  @Override
  public int countBySupplier(Long supplierSeq) {
    return 0;
  }
}
