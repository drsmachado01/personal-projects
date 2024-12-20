package br.com.oba.axe.task_manager.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.oba.axe.task_manager.application.request.LoginUserRequestDTO;
import br.com.oba.axe.task_manager.application.request.RegisterUserRequestDTO;
import br.com.oba.axe.task_manager.domain.User;
import br.com.oba.axe.task_manager.domain.repository.UserRepository;

@Service
public class AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	public User signup(RegisterUserRequestDTO input) {
		User user = User.builder().fullName(input.getFullName()).email(input.getEmail())
				.password(passwordEncoder.encode(input.getPassword())).build();

		return userRepository.save(user);
	}

	public User authenticate(LoginUserRequestDTO input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return userRepository.findByEmail(input.getEmail()).orElseThrow();
	}
}
