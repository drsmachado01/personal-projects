package br.com.oba.axe.user_mgmt.services;

import br.com.oba.axe.user_mgmt.dto.request.LoginUserRequestDTO;
import br.com.oba.axe.user_mgmt.dto.request.RegisterUserRequestDTO;
import br.com.oba.axe.user_mgmt.entities.Role;
import br.com.oba.axe.user_mgmt.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.oba.axe.user_mgmt.repositories.RoleRepository;
import br.com.oba.axe.user_mgmt.repositories.UserRepository;
import br.com.oba.axe.user_mgmt.util.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public AuthenticationService(RoleRepository roleRepository,
								UserRepository userRepository, 
								AuthenticationManager authenticationManager,
								PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User signup(RegisterUserRequestDTO input) {
	    Optional<Role> optionalRole = roleRepository.findByRole(UserRole.USER);
        
	    if (optionalRole.isEmpty()) {
	        return null;
	    }
	    
		var user = User
				.builder()
				.fullName(input.getFullName())
				.email(input.getEmail())
				.password(passwordEncoder.encode(input.getPassword()))
				.role(optionalRole.get())
				.build();

		return userRepository.save(user);
	}

	public User authenticate(LoginUserRequestDTO input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
		return userRepository.findByEmail(input.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
	}

	public List<User> allUsers() {
        return new ArrayList<>(userRepository.findAll());
	}
}
