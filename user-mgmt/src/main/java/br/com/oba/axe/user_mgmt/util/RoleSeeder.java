package br.com.oba.axe.user_mgmt.util;

import br.com.oba.axe.user_mgmt.entities.Role;
import br.com.oba.axe.user_mgmt.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.loadRoles();
    }

    private void loadRoles() {
        List<UserRole> roleNames = List.of(UserRole.USER, UserRole.ADMIN, UserRole.SUPER_ADMIN);

        Map<UserRole, String> roleDescriptionMap = Map.of(
                UserRole.USER, "Default user role",
                UserRole.ADMIN, "Adminstratior role",
                UserRole.SUPER_ADMIN, "Super Administrator role"
        );

        roleNames.forEach(roleName -> {
            Optional<Role> optionalRole = roleRepository.findByRole(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = Role
                		.builder()
                		.role(roleName)
                		.description(roleDescriptionMap.get(roleName))
                		.build();

                roleRepository.save(roleToCreate);
            });
        });
    }
}
