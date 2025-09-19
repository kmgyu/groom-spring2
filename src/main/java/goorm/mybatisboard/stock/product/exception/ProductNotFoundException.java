package goorm.mybatisboard.stock.product.exception;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String message) {
    super(message);
  }
  public ProductNotFoundException(Long message) {
    super("Product " + message + " not found");
  }
}
