package com.outercode.caution.controllers;

import com.outercode.caution.dto.LoadItemDTO.CreateLoadItemRequestDTO;
import com.outercode.caution.dto.LoadItemDTO.LoadItemResponse;
import com.outercode.caution.dto.loadDTO.CreateLoadRequestDTO;
import com.outercode.caution.dto.loadDTO.LoadResponse;
import com.outercode.caution.services.imp.LoadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "{userId}/loads", produces = "application/json")
@RequiredArgsConstructor
public class LoadController {

    private final LoadService loadService;

    @PostMapping
    ResponseEntity<LoadResponse> create(@PathVariable UUID userId,
                                        @RequestBody @Valid CreateLoadRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loadService.create(userId, dto));
    }

    @PostMapping("/{loadId}/items/{materialId}")
    ResponseEntity<LoadItemResponse> addLoadItem(@PathVariable UUID userId,
                                                 @PathVariable UUID loadId,
                                                 @PathVariable UUID materialId,
                                                 @RequestBody @Valid CreateLoadItemRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loadService.addLoadItem(userId, loadId, materialId, dto));
    }
}
