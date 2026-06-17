package com.outercode.caution.controllers;

import com.outercode.caution.dto.cautionDTO.CautionResponse;
import com.outercode.caution.dto.cautionDTO.CreateCautionRequestDTO;
import com.outercode.caution.services.ICautionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "{userId}/cautions", produces = "application/json")
@RequiredArgsConstructor
public class CautionController {

    private final ICautionService cautionService;

    @PostMapping
    public ResponseEntity<CautionResponse> create(
            @PathVariable UUID userId,
            @RequestBody @Valid CreateCautionRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cautionService.create(userId, dto));
    }

    @GetMapping
    public ResponseEntity<List<CautionResponse>> findAll(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(cautionService.findAll(userId, page, size));
    }
}
