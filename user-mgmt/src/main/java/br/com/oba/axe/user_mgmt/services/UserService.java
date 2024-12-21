package br.com.oba.axe.user_mgmt.services;

import br.com.oba.axe.user_mgmt.dto.request.RegisterUserRequestDTO;
import br.com.oba.axe.user_mgmt.entities.Role;
import br.com.oba.axe.user_mgmt.entities.User;
import br.com.oba.axe.user_mgmt.repositories.RoleRepository;
import br.com.oba.axe.user_mgmt.repositories.UserRepository;
import br.com.oba.axe.user_mgmt.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> allUsers() {

        return new ArrayList<>(userRepository.findAll());
    }



    public User createAdministrator(RegisterUserRequestDTO input) {
        Optional<Role> optionalRole = roleRepository.findByRole(UserRole.ADMIN);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = User.builder()
                .fullName(input.getFullName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .role(optionalRole.get())
                .build();

        return userRepository.save(user);
    }
}