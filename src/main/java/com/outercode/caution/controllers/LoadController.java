package com.outercode.caution.controllers;

import com.outercode.caution.dto.loadDTO.CreateLoadRequestDTO;
import com.outercode.caution.dto.loadDTO.LoadResponse;
import com.outercode.caution.services.imp.LoadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "{userId}/loads", produces = "application/json")
@RequiredArgsConstructor
public class LoadController {

    private final LoadService loadService;

    @PostMapping
    ResponseEntity<LoadResponse> create (@PathVariable UUID userId,
                                         @RequestBody @Valid CreateLoadRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loadService.create(userId, dto));
    }
}
