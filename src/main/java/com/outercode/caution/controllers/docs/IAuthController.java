package com.outercode.caution.controllers.docs;

import com.outercode.caution.dto.authDTO.LoginRequestDTO;
import com.outercode.caution.dto.authDTO.LoginResponseDTO;
import com.outercode.caution.dto.authDTO.RegisterRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthController {
    ResponseEntity<LoginResponseDTO> login (@RequestBody @Valid LoginRequestDTO data);
    ResponseEntity<?> register (@RequestBody @Valid RegisterRequestDTO data);
}
