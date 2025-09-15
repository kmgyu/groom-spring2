package goorm.mybatisboard.auth;

import goorm.mybatisboard.auth.Service.UserServiceImpl;
import goorm.mybatisboard.auth.dto.LoginDto;
import goorm.mybatisboard.auth.dto.ProfileUpdateDto;
import goorm.mybatisboard.auth.dto.SignupDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceImpl userServiceImpl;
    private final MessageSource messageSource;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupDTO", new SignupDto());
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignupDto signupDTO,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Locale locale) {

        if (result.hasErrors()) {
            return "auth/signup";
        }

        try {
            User user = userServiceImpl.signup(signupDTO);
            String message = messageSource.getMessage("flash.user.created", null, locale);
            redirectAttributes.addFlashAttribute("successMessage", message);
            return "redirect:/";
        } catch (Exception e) {
            result.reject("signup.failed", e.getMessage());
            return "auth/signup";
        }
    }

    // spring security가 login 진행 과정 담당
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginDTO", new LoginDto());
        return "auth/login";
    }

    @GetMapping("/profile")
    public String profileForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        // 최신 사용자 정보 조회
        User currentUser = userServiceImpl.findById(user.getId());

        // 프로필 DTO 생성 및 기본값 설정
        ProfileUpdateDto profileUpdateDto = new ProfileUpdateDto();
        profileUpdateDto.setNickname(currentUser.getNickname());

        model.addAttribute("profileUpdateDto", profileUpdateDto);
        model.addAttribute("currentUser", currentUser);

        return "auth/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute ProfileUpdateDto profileUpdateDto,
                                BindingResult result,
                                HttpSession session,
                                RedirectAttributes redirectAttributes,
                                Model model,
                                Locale locale) {

        User user = (User) session.getAttribute("user");

        if (result.hasErrors()) {
            User currentUser = userServiceImpl.findById(user.getId());
            model.addAttribute("currentUser", currentUser);
            return "auth/profile";
        }

        try {
            User updatedUser = userServiceImpl.updateProfile(user.getId(), profileUpdateDto);

            // 세션의 사용자 정보 업데이트
            session.setAttribute("user", updatedUser);

            String message = messageSource.getMessage("flash.profile.updated", null, "프로필이 수정되었습니다.", locale);
            redirectAttributes.addFlashAttribute("successMessage", message);
            return "redirect:/auth/profile";
        } catch (Exception e) {
            result.reject("profile.update.failed", e.getMessage());
            User currentUser = userServiceImpl.findById(user.getId());
            model.addAttribute("currentUser", currentUser);
            return "auth/profile";
        }
    }

//    @GetMapping("/admin")
//    public String admin(HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user == null || !user.isAdmin()) {
//            throw new AccessDeniedException("관리자 권한이 필요합니다.");
//        }
//        return "admin";
//    }
}
