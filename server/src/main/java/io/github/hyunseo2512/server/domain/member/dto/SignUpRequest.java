package io.github.hyunseo2512.server.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record SignUpRequest(
        @NotBlank(message = "아이디는 필수입니다.")
        @Length(min = 4, max = 20, message = "아이디는 4~20자여야 합니다.")
        @Pattern(regexp = "^[a-z0-9_-]+$", message = "아이디는 영문 소문자, 숫자, 특수기호(_,-)만 사용 가능합니다.")
        String username,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Length(min = 8, max = 50, message = "비밀번호는 8~50자여야 합니다.")
        String password,

        @NotBlank(message = "이름은 필수입니다.")
        @Length(min = 2, max = 10, message = "이름은 2~10자여야 합니다.")
        String name,

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email
) {
}
