package com.outercode.caution.services.imp;

import com.outercode.caution.entities.User;
import com.outercode.caution.entities.enums.Role;
import com.outercode.caution.repositories.UserRepository;
import com.outercode.caution.services.imp.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void loadUserByUsernameShouldReturnUserDetailsWhenEmailExists() {
        User user = new User("Admin", "admin@example.com", "secret", Role.ARMORER, "Sgt");
        when(userRepository.findByEmail("admin@example.com")).thenReturn(Optional.of((UserDetails) user));

        UserDetails result = authService.loadUserByUsername("admin@example.com");

        assertSame(user, result);
    }

    @Test
    void loadUserByUsernameShouldThrowWhenEmailDoesNotExist() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> authService.loadUserByUsername("missing@example.com"));
    }
}
