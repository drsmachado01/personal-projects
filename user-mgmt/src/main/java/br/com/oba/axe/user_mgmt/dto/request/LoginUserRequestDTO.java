package br.com.oba.axe.user_mgmt.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequestDTO {
    private String email;
    private String password;
}
