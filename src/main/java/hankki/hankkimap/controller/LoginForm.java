package hankki.hankkimap.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    @NotEmpty(message="아이디를 입력해주세요.")
    private String id;

    @NotEmpty(message="비밀번호를 입력해주세요.")
    private String pw;


}
