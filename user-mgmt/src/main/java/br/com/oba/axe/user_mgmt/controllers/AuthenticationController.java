package br.com.oba.axe.user_mgmt.controllers;

import br.com.oba.axe.user_mgmt.aspect.annotation.Logifier;
import br.com.oba.axe.user_mgmt.dto.request.LoginUserRequestDTO;
import br.com.oba.axe.user_mgmt.dto.request.RegisterUserRequestDTO;
import br.com.oba.axe.user_mgmt.dto.response.LoginResponseDTO;
import br.com.oba.axe.user_mgmt.entities.User;
import br.com.oba.axe.user_mgmt.services.AuthenticationService;
import br.com.oba.axe.user_mgmt.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    @Logifier
    public ResponseEntity<User> register(@RequestBody RegisterUserRequestDTO registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping(value = "/login")
    @Logifier
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginUserRequestDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        return ResponseEntity.ok(LoginResponseDTO.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).build());
    }
}