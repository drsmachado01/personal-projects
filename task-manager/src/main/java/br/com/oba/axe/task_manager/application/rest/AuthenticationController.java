package br.com.oba.axe.task_manager.application.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.oba.axe.task_manager.application.request.LoginUserRequestDTO;
import br.com.oba.axe.task_manager.application.request.RegisterUserRequestDTO;
import br.com.oba.axe.task_manager.application.response.LoginUserResponseDTO;
import br.com.oba.axe.task_manager.application.response.UserResponseDTO;
import br.com.oba.axe.task_manager.aspect.annotation.Logifier;
import br.com.oba.axe.task_manager.domain.User;
import br.com.oba.axe.task_manager.domain.service.AuthenticationService;
import br.com.oba.axe.task_manager.domain.service.DomainJwtService;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
	private final DomainJwtService jwtService;

	private final AuthenticationService authenticationService;

	@Autowired
	@Logifier
	public AuthenticationController(DomainJwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	@Logifier
	public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterUserRequestDTO registerUserDto) {
		return ResponseEntity.ok(new UserResponseDTO(authenticationService.signup(registerUserDto)));
	}

	@PostMapping("/login")
	@Logifier
	public ResponseEntity<LoginUserResponseDTO> authenticate(@RequestBody LoginUserRequestDTO loginUserDto) {
		User authenticatedUser = authenticationService.authenticate(loginUserDto);

		String jwtToken = jwtService.generateToken(authenticatedUser);

		LoginUserResponseDTO loginResponse = LoginUserResponseDTO.builder().token(jwtToken)
				.expiresIn(jwtService.getExpirationTime()).build();

		return ResponseEntity.ok(loginResponse);
	}

}
