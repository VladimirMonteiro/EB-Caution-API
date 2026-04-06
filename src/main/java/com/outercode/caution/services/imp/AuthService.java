package com.outercode.caution.services.imp;

import com.outercode.caution.repositories.UserRepository;
import com.outercode.caution.services.imp.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new ObjectNotFoundException("Usuário não encontrado."));

    }
}
