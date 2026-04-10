package com.outercode.caution.controllers;

import com.outercode.caution.dto.militaryDTO.CreateMilitaryRequestDTO;
import com.outercode.caution.dto.militaryDTO.MilitaryResponse;
import com.outercode.caution.services.IMilitaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "militaries", produces = "application/json")
@RequiredArgsConstructor
public class MilitaryController {

    private final IMilitaryService militaryService;

    @PostMapping
    ResponseEntity<MilitaryResponse> create (@RequestBody @Valid CreateMilitaryRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(militaryService.create(dto));
    }

    @GetMapping("/{userId}")
    ResponseEntity<List<MilitaryResponse>> findAll(@PathVariable UUID userId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "15") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(militaryService.findAll(userId, page, size));
    }
}
