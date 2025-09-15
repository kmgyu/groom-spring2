package goorm.mybatisboard.auth.Service;

import goorm.mybatisboard.auth.User;
import goorm.mybatisboard.auth.UserMapper;
import goorm.mybatisboard.auth.dto.LoginDto;
import goorm.mybatisboard.auth.dto.ProfileUpdateDto;
import goorm.mybatisboard.auth.dto.SignupDto;
import goorm.mybatisboard.auth.exception.DuplicateEmailException;
import goorm.mybatisboard.auth.exception.InvalidCredentialsException;
import goorm.mybatisboard.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("userServiceMyBatis")
@Profile("mybatis")
@RequiredArgsConstructor
@Transactional
public class MyBatisUserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    public User signup(SignupDto signupDto) {
        // 이메일 중복 검사
        if (userMapper.existsByEmail(signupDto.getEmail())) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }
        
        // 비밀번호 확인 검사
        if (!signupDto.getPassword().equals(signupDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        
        // 사용자 엔티티 생성
        User user = new User();
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword())); // BCrypt 암호화
        user.setNickname(signupDto.getNickname());
        
        userMapper.insert(user);
        return user;
    }
    
    @Transactional(readOnly = true)
    public User authenticate(LoginDto loginDto) {
        Optional<User> userOptional = userMapper.findByEmail(loginDto.getEmail());
        
        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        
        User user = userOptional.get();
        
        // BCrypt 비밀번호 검증
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        
        return user;
    }
    
    public User updateProfile(Long userId, ProfileUpdateDto profileUpdateDto) {
        User user = userMapper.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        
        // 현재 비밀번호 확인 (BCrypt 비교)
        if (!passwordEncoder.matches(profileUpdateDto.getCurrentPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("현재 비밀번호가 일치하지 않습니다.");
        }
        
        // 닉네임 업데이트
        user.setNickname(profileUpdateDto.getNickname());
        
        // 새 비밀번호가 입력된 경우 비밀번호 변경
        if (profileUpdateDto.getNewPassword() != null && !profileUpdateDto.getNewPassword().isEmpty()) {
            // 새 비밀번호 확인 검사
            if (!profileUpdateDto.getNewPassword().equals(profileUpdateDto.getNewPasswordConfirm())) {
                throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
            }
            
            // BCrypt로 암호화하여 저장
            user.setPassword(passwordEncoder.encode(profileUpdateDto.getNewPassword()));
        }
        
        userMapper.update(user);
        return user;
    }
    
    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userMapper.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }
    
    // UserDetailsService 구현 - Spring Security에서 사용
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userMapper.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));
    }
}