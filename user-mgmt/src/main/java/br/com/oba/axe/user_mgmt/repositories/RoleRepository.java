package br.com.oba.axe.user_mgmt.repositories;

import br.com.oba.axe.user_mgmt.entities.Role;
import br.com.oba.axe.user_mgmt.util.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByRole(UserRole role);
}
