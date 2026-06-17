package com.outercode.caution.controllers;

import com.outercode.caution.dto.authDTO.LoginRequestDTO;
import com.outercode.caution.dto.authDTO.RegisterRequestDTO;
import com.outercode.caution.entities.User;
import com.outercode.caution.entities.enums.Role;
import com.outercode.caution.infra.security.TokenService;
import com.outercode.caution.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthController authController;

    @Test
    void loginShouldAuthenticateAndReturnGeneratedToken() {
        LoginRequestDTO request = new LoginRequestDTO("admin@example.com", "secret");
        User user = new User("Admin", "admin@example.com", "encoded", Role.ARMORER, "Sgt");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("jwt-token");

        var response = authController.login(request);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("jwt-token", response.getBody().token());
    }

    @Test
    void registerShouldReturnBadRequestWhenEmailAlreadyExists() {
        RegisterRequestDTO request = new RegisterRequestDTO("Admin", "admin@example.com", "secret", "secret", Role.ARMORER, "Sgt");
        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(new User("Admin", "admin@example.com", "encoded", Role.ARMORER, "Sgt")));

        var response = authController.register(request);

        assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerShouldEncodePasswordAndPersistUserWhenEmailIsAvailable() {
        RegisterRequestDTO request = new RegisterRequestDTO("Admin", "admin@example.com", "secret", "secret", Role.SUB_ARMORER, "Cb");
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.empty());

        var response = authController.register(request);

        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("Admin", savedUser.getWarName());
        assertEquals("admin@example.com", savedUser.getEmail());
        assertEquals(Role.SUB_ARMORER, savedUser.getRole());
        assertEquals("Cb", savedUser.getGrad());
        assertTrue(new BCryptPasswordEncoder().matches("secret", savedUser.getPassword()));
    }
}
