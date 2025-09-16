package goorm.mybatisboard.product;

import goorm.mybatisboard.auth.security.LoginFailureHandler;
import goorm.mybatisboard.product.dto.ProductDto;
import goorm.mybatisboard.product.dto.ProductSearchDto;
import goorm.mybatisboard.product.service.CategoryService;
import goorm.mybatisboard.product.service.ProductService;
import goorm.mybatisboard.product.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/product")
public class ProductController {
  private final ProductService productService;
  private final CategoryService categoryService;
  private final SupplierService supplierService;
  private final MessageSource messageSource;
  private final LocaleResolver localeResolver;
//  private final LoginFailureHandler loginFailureHandler;

  /**
   * 상품 목록 조회
   */
  @GetMapping
  public String list(
          @ModelAttribute ProductSearchDto searchDto,
          Model model
  ) {
    log.debug("Product list request with search: {}", searchDto);

    Page<ProductDto> products = productService.findAll(searchDto);

    model.addAttribute("products", products);
    model.addAttribute("search", searchDto);
    model.addAttribute("totalElements", products.getTotalElements());
    model.addAttribute("totalPages", products.getTotalPages());
    model.addAttribute("currentPage", products.getNumber() + 1);

    // 검색 필터용 데이터
    model.addAttribute("categories", categoryService.findAllActive());
    model.addAttribute("productStatuses", ProductStatus.values());

    return "products/list";
  }
}
