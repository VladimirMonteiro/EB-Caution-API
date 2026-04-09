package com.outercode.caution.controllers;

import com.outercode.caution.dto.militaryDTO.CreateMilitaryRequestDTO;
import com.outercode.caution.dto.militaryDTO.MilitaryResponse;
import com.outercode.caution.services.IMilitaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "militaries", produces = "application/json")
@RequiredArgsConstructor
public class MilitaryController {

    private final IMilitaryService militaryService;

    @PostMapping
    ResponseEntity<MilitaryResponse> create (@RequestBody @Valid CreateMilitaryRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(militaryService.create(dto));
    }
}
