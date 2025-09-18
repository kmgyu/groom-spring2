package goorm.mybatisboard.product.exception;

public class ProductValidationException extends RuntimeException {
  public ProductValidationException(String message) {
    super(message);
  }
}
