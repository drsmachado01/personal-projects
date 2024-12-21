package br.com.oba.axe.user_mgmt.repositories;

import java.util.Optional;

import br.com.oba.axe.user_mgmt.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	Optional<User> findByEmail(String email);
}
