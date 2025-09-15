package goorm.mybatisboard.auth.dto2;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupDto {

    @Email(message = "{validation.email.invalid}")
    @NotBlank(message = "{validation.email.required}")
    private String email;

    @Size(min=2, max=20, message = "{validation.nickname.size}")
    @NotBlank(message = "{validation.nickname.required}")
    private String nickname;

    @Size(min=4, message = "{validation.password.size}")
    @NotBlank(message = "{validation.password.required}")
    private String password;

    @NotBlank(message = "{validation.password.confirm.required}")
    private String passwordConfirm;
}
