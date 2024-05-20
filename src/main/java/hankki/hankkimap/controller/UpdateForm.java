package hankki.hankkimap.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateForm {
    private String id;

    private String pw;

    private String name;

    private String email;

    private String phone;

}
