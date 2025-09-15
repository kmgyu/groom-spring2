package goorm.mybatisboard.auth.Service;

import goorm.mybatisboard.auth.User;
import goorm.mybatisboard.auth.dto.LoginDto;
import goorm.mybatisboard.auth.dto.ProfileUpdateDto;
import goorm.mybatisboard.auth.dto.SignupDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    
    User signup(SignupDto signupDto);
    
    User authenticate(LoginDto loginDto);
    
    User updateProfile(Long userId, ProfileUpdateDto profileUpdateDto);
    
    User findById(Long userId);
}