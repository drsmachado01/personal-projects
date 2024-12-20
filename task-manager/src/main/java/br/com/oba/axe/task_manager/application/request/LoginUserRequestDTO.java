package br.com.oba.axe.task_manager.application.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class LoginUserRequestDTO {
    private String email;
    
    private String password;

}
