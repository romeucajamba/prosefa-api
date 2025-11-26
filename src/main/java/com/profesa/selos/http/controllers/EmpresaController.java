package com.profesa.selos.http.controllers;
import com.profesa.selos.domain.entities.Empresa;
import com.profesa.selos.dto.EmpresaUpdateDto;
import com.profesa.selos.services.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.profesa.selos.dto.EmpresaCreateDto;
import com.profesa.selos.dto.EmpresaResponse;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/empresa")
@RequiredArgsConstructor
public class EmpresaController {
    private final EmpresaService service;

    @PostMapping("/criar")
    public ResponseEntity<EmpresaResponse> criarEmpresa(@Valid @RequestBody EmpresaCreateDto data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.criar(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponse> buscarEmpresaPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EmpresaResponse> atualizarStatus(
            @PathVariable UUID id,
            @Valid @RequestBody EmpresaUpdateDto data
    ) {
        return ResponseEntity.ok(service.atualizarStatus(id, data));
    }
}
