package com.seonggyun.escapelog.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemberSignUpForm {
    @NotBlank(message = "로그인 id를 입력해주세요.")
    String loginId;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password;
    @NotBlank(message = "이름을 입력해주세요.")
    String name;
}
