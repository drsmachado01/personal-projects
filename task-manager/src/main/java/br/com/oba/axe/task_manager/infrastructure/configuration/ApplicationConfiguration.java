package br.com.oba.axe.task_manager.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.oba.axe.task_manager.domain.repository.UserRepository;
import br.com.oba.axe.task_manager.infrastructure.InfrastructureException;

@Configuration
public class ApplicationConfiguration {
	private final UserRepository repository;
	
	@Autowired
	public ApplicationConfiguration(final UserRepository repository) {
		this.repository = repository;
	}

    @Bean
    UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username).orElseThrow(() -> new InfrastructureException("User not found"));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }	
	
}
