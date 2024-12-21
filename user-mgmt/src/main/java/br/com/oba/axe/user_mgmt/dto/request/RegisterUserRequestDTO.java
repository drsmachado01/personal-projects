package br.com.oba.axe.user_mgmt.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterUserRequestDTO {
    private String email;
    private String password;
    private String fullName;
}
