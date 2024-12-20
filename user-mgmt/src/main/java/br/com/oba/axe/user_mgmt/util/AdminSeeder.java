package br.com.oba.axe.user_mgmt.util;

import br.com.oba.axe.user_mgmt.dto.request.RegisterUserRequestDTO;
import br.com.oba.axe.user_mgmt.entities.Role;
import br.com.oba.axe.user_mgmt.entities.User;
import br.com.oba.axe.user_mgmt.repositories.RoleRepository;
import br.com.oba.axe.user_mgmt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminSeeder(
            RoleRepository roleRepository,
            UserRepository  userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterUserRequestDTO userDto = RegisterUserRequestDTO
                .builder()
                .fullName("Super Admin")
                .email("super.admin@email.com")
                .password("123456")
                .build();

        Optional<Role> optionalRole = roleRepository.findByRole(UserRole.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = User
                .builder()
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(optionalRole.get())
                .build();

        userRepository.save(user);
    }
}