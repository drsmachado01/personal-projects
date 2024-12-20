package br.com.oba.axe.task_manager.application.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class LoginUserResponseDTO {
	private String token;

	private long expiresIn;
}
