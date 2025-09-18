package goorm.mybatisboard.product.exception;

public class ProductCodeDuplicateException extends RuntimeException {
  public ProductCodeDuplicateException(String message) {
    super(message);
  }
}
