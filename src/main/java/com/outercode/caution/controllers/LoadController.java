package com.outercode.caution.controllers;

import com.outercode.caution.dto.LoadItemDTO.CreateLoadItemRequestDTO;
import com.outercode.caution.dto.LoadItemDTO.LoadItemResponse;
import com.outercode.caution.dto.LoadItemDTO.UpdateLoadItemRequestDTO;
import com.outercode.caution.dto.loadDTO.CreateLoadRequestDTO;
import com.outercode.caution.dto.loadDTO.LoadDetailsResponseDTO;
import com.outercode.caution.dto.loadDTO.LoadResponse;
import com.outercode.caution.services.imp.LoadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping
    public ResponseEntity<List<LoadResponse>> findAll(@PathVariable UUID userId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(loadService.findAll(userId, page, size));
    }

    @GetMapping("/{loadId}")
    public ResponseEntity<LoadDetailsResponseDTO> findById(@PathVariable UUID userId,
                                                           @PathVariable UUID loadId) {
        return ResponseEntity.status(HttpStatus.OK).body(loadService.findById(userId, loadId));
    }

    @DeleteMapping("/{loadId}/items/{materialId}")
    ResponseEntity<Void> removeLoadItem(@PathVariable UUID userId,
                                        @PathVariable UUID loadId,
                                        @PathVariable UUID materialId) {
        loadService.removeLoadItem(userId, loadId, materialId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{loadId}/items/{materialId}")
    public ResponseEntity<LoadItemResponse> updateLoadItem(@PathVariable UUID userId,
                                                           @PathVariable UUID loadId,
                                                           @PathVariable UUID materialId,
                                                           @RequestBody UpdateLoadItemRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(loadService.updateLoadItem(userId, loadId, materialId, dto));
    }
}
