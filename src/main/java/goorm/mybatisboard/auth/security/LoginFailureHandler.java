package goorm.mybatisboard.auth.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  // 실패 후 돌아갈 로그인 페이지
  private static final String FAILURE_PAGE = "/auth/login";

  @Override
  public void onAuthenticationFailure(HttpServletRequest request,
                                      HttpServletResponse response,
                                      AuthenticationException ex)
          throws IOException, ServletException {

    String code = mapExceptionToCode(ex);

    // 실패 로그(민감정보 노출 금지)
    String email = request.getParameter("email"); // .usernameParameter("email") 사용 중
    log.warn("로그인 실패: email={}, code={}, ex={}", email, code, ex.getClass().getSimpleName());

    // 필요하면 메시지도 같이(템플릿에서 쓰지 않으면 생략 가능)
    String msg = mapCodeToMessage(code);
    String redirectUrl = FAILURE_PAGE
            + "?error=" + url(code)
            + "&message=" + url(msg);

    setDefaultFailureUrl(redirectUrl);
    super.onAuthenticationFailure(request, response, ex);
  }

  private String mapExceptionToCode(AuthenticationException ex) {
    if (ex instanceof BadCredentialsException)      return "bad_credentials";
    if (ex instanceof LockedException)              return "locked";
    if (ex instanceof DisabledException)            return "disabled";
    if (ex instanceof AccountExpiredException)      return "account_expired";
    if (ex instanceof CredentialsExpiredException)  return "credentials_expired";
    return "unknown";
  }

  private String mapCodeToMessage(String code) {
    return switch (code) {
      case "bad_credentials"     -> "이메일 또는 비밀번호가 올바르지 않습니다.";
      case "locked"              -> "계정이 잠겨 있습니다.";
      case "disabled"            -> "비활성화된 계정입니다.";
      case "account_expired"     -> "계정이 만료되었습니다.";
      case "credentials_expired" -> "비밀번호가 만료되었습니다.";
      default                    -> "로그인에 실패했습니다.";
    };
  }

  private String url(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }
}
