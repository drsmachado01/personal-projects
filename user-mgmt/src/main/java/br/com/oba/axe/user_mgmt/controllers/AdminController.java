package br.com.oba.axe.user_mgmt.controllers;

import br.com.oba.axe.user_mgmt.aspect.annotation.Logifier;
import br.com.oba.axe.user_mgmt.dto.request.RegisterUserRequestDTO;
import br.com.oba.axe.user_mgmt.entities.User;
import br.com.oba.axe.user_mgmt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admins")
@RestController
public class AdminController {
    private final UserService userService;

    @Autowired
    @Logifier
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Logifier
    public ResponseEntity<User> createAdministrator(@RequestBody RegisterUserRequestDTO registerUserDto) {
        User createdAdmin = userService.createAdministrator(registerUserDto);

        return ResponseEntity.ok(createdAdmin);
    }
}