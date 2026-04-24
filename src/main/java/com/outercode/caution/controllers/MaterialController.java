package com.outercode.caution.controllers;

import com.outercode.caution.dto.materialDTO.CreateMaterialRequestDTO;
import com.outercode.caution.dto.materialDTO.MaterialResponse;
import com.outercode.caution.services.imp.MaterialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "materials", produces = "application/json")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @PostMapping
    public ResponseEntity<MaterialResponse> create (@RequestBody @Valid CreateMaterialRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(materialService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<MaterialResponse>> findAll(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(materialService.findAll(page, size));
    }
}
