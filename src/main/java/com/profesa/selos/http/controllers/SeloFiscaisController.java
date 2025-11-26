package com.profesa.selos.http.controllers;

import com.profesa.selos.domain.entities.SelosFiscais;
import com.profesa.selos.dto.SeloResponse;
import com.profesa.selos.dto.SelosDto;
import com.profesa.selos.services.SelosFiscaisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/selos")
@RequiredArgsConstructor
public class SeloFiscaisController {
    private final SelosFiscaisService service;

    @PostMapping("/solicitar")
    public ResponseEntity<SeloResponse> solicitar(
            @Valid @RequestBody SelosDto request,
            @RequestHeader(value = "X-User", required = false) String user
    ){
        // fallback para usuário anônimo se header não fornecido
        String usuario = (user == null || user.isBlank()) ? "system" : user;

        SelosFiscais selo = service.solicitarSelo(request.empresaId(), request.produto(), usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SeloResponse.fromEntity(selo));
    }

    @PostMapping("/validar/{codigo}")
    public ResponseEntity<Void> validar(
            @PathVariable String codigo,
            @RequestHeader(value = "X-User", required = false) String user
    ){
        String usuario = (user == null || user.isBlank()) ? "system" : user;
        service.validarSelo(codigo, usuario);
        return ResponseEntity.ok().build();
    }
}
