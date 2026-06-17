package com.outercode.caution.controllers;

import com.outercode.caution.controllers.docs.IAuthController;
import com.outercode.caution.dto.authDTO.LoginRequestDTO;
import com.outercode.caution.dto.authDTO.LoginResponseDTO;
import com.outercode.caution.dto.authDTO.RegisterRequestDTO;
import com.outercode.caution.entities.User;
import com.outercode.caution.entities.enums.Role;
import com.outercode.caution.infra.security.TokenService;
import com.outercode.caution.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(value = "/auth", produces = "application/json")
@RequiredArgsConstructor
public class AuthController implements IAuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody @Valid LoginRequestDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = this.tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok().body(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody @Valid RegisterRequestDTO data) {

        if (this.userRepository.findByEmail(data.email()).isPresent()) return ResponseEntity.badRequest().build();

        if (!Objects.equals(data.password(), data.passwordConfirm())) {
            throw new RuntimeException("As senhas não conferem.");
        }

        var role = data.role() != null ? data.role() : Role.ARMORER;
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.warName(), data.email(), encryptedPassword, role , data.grad());

        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}