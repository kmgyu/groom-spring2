package goorm.mybatisboard.handler;

import goorm.mybatisboard.auth.dto.LoginDto;
import goorm.mybatisboard.auth.dto.SignupDto;
import goorm.mybatisboard.auth.exception.AccessDeniedException;
import goorm.mybatisboard.auth.exception.DuplicateEmailException;
import goorm.mybatisboard.auth.exception.InvalidCredentialsException;
import goorm.mybatisboard.auth.exception.UserNotFoundException;
import goorm.mybatisboard.post.exception.PostNotFoundException;
import goorm.mybatisboard.product.exception.ExcelExportException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 전역 예외 처리기
 * 모든 컨트롤러에서 발생하는 예외를 한 곳에서 처리
 */
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    /**
     * PostNotFoundException 처리
     * 게시글을 찾을 수 없을 때 404 페이지로 이동
     */
    @ExceptionHandler(PostNotFoundException.class)
    public String handlePostNotFoundException(PostNotFoundException e, Model model) {
        log.warn("Post not found: {}", e.getMessage());

        // 국제화 메시지 조회
        String errorMessage = messageSource.getMessage(
                "error.post.notfound",
                null,
                LocaleContextHolder.getLocale()
        );

        model.addAttribute("error", errorMessage);
        model.addAttribute("postId", e.getPostId());

        return "error/404";
    }

    /**
     * DuplicateEmailException 처리
     * 이메일 중복 시 회원가입 폼으로 리다이렉트
     */
    @ExceptionHandler(DuplicateEmailException.class)
    public String handleDuplicateEmailException(DuplicateEmailException e, Model model) {
        log.warn("Duplicate email: {}", e.getMessage());

        // 국제화 메시지 조회
        String errorMessage = messageSource.getMessage(
                "error.email.duplicate",
                null,
                LocaleContextHolder.getLocale()
        );

        model.addAttribute("error", errorMessage);
        model.addAttribute("signupDto", new SignupDto());

        return "auth/signup";
    }

    /**
     * InvalidCredentialsException 처리
     * 로그인 실패 시 로그인 폼으로 리다이렉트
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public String handleInvalidCredentialsException(InvalidCredentialsException e, Model model) {
        log.warn("Invalid credentials: {}", e.getMessage());

        // 국제화 메시지 조회
        String errorMessage = messageSource.getMessage(
                "error.login.invalid",
                null,
                LocaleContextHolder.getLocale()
        );

        model.addAttribute("error", errorMessage);
        model.addAttribute("loginDto", new LoginDto());

        return "auth/login";
    }

    /**
     * UserNotFoundException 처리
     * 사용자 조회 실패 시 메인 페이지로 리다이렉트
     */
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e, Model model) {
        log.warn("User not found: {}", e.getMessage());

        // 국제화 메시지 조회
        String errorMessage = messageSource.getMessage(
                "error.user.notfound",
                null,
                LocaleContextHolder.getLocale()
        );

        model.addAttribute("error", errorMessage);

        return "error/404";
    }

    /**
     * AccessDeniedException 처리
     * 권한 없는 접근 시 게시글 상세 페이지로 리다이렉트
     */
    @ExceptionHandler(AccessDeniedException.class)
    public String handleSpringSecurityAccessDenied(AccessDeniedException e, Model model, HttpServletRequest request) {
        log.warn("Access denied: {} - {}", request.getRequestURI(), e.getMessage());

        // 국제화 메시지 조회
        String errorMessage = messageSource.getMessage(
                "error.post.access.denied",
                null,
                LocaleContextHolder.getLocale()
        );

        model.addAttribute("error", errorMessage);

        return "error/403";
    }

    /**
     * AuthenticationException 처리
     * 인증 필요한 요청 시 로그인 페이지로 리다이렉트
     */
    @ExceptionHandler(AuthenticationException.class)
    public String handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
        log.warn("Authentication required: {} - {}", request.getRequestURI(), e.getMessage());

        // 원래 요청 URL을 로그인 후 리다이렉트를 위해 저장
        String redirectUrl = request.getRequestURI();
        if (request.getQueryString() != null) {
            redirectUrl += "?" + request.getQueryString();
        }

        return "redirect:/auth/login?redirect=" + URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8);
    }

    /**
     * 기타 모든 예외 처리
     * 예상하지 못한 예외가 발생할 때 500 페이지로 이동
     */
    /**
     * Excel 관련 예외 처리
     * Excel 내보내기 중 발생한 예외를 처리
     */
    @ExceptionHandler(ExcelExportException.class)
    public ResponseEntity<String> handleExcelExportException(ExcelExportException e, HttpServletRequest request) {
        log.error("Excel export error: {} - {}", e.getErrorCode(), e.getMessage(), e);

        // 이미 다국어 처리된 메시지를 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        log.error("Unexpected error occurred", e);

        // 국제화 메시지 조회
        String errorMessage = messageSource.getMessage(
                "error.server.internal",
                null,
                LocaleContextHolder.getLocale()
        );

        model.addAttribute("error", errorMessage);

        // 개발 환경에서는 상세 에러 정보 표시
        if (log.isDebugEnabled()) {
            model.addAttribute("exception", e.getClass().getSimpleName());
            model.addAttribute("message", e.getMessage());
        }

        return "error/500";
    }
}