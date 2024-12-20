package br.com.oba.axe.task_manager.application.response;

import java.time.LocalDate;

import br.com.oba.axe.task_manager.domain.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class UserResponseDTO {
	private Long id;
	private String fullName;
	private String email;
	private String password;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	
	public UserResponseDTO(User user) {
		this.id = user.getId();
		this.fullName = user.getFullName();
		this.email = user.getEmail();
		this.createdAt = user.getCreatedAt();
		this.updatedAt = user.getUpdatedAt();
	}
}
