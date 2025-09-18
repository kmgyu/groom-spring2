package goorm.mybatisboard.post.exception;

public class PostNotFoundException extends RuntimeException {
  private final Long postId;

  public PostNotFoundException(String message) {
    super("Post Not Found" + message);
    this.postId = null;
  }
  public PostNotFoundException(Long postId) {
    super("Post Not Found" + postId);
    this.postId = postId;
  }

  public Long getPostId() {
    return postId;
  }
}
