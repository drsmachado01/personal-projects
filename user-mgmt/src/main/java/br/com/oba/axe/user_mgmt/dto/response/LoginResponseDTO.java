package br.com.oba.axe.user_mgmt.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponseDTO {
    private String token;

    private long expiresIn;
}
