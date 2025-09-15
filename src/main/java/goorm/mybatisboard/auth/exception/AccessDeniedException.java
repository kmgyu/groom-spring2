package goorm.mybatisboard.auth.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super("access denied" + message);
    }
}
