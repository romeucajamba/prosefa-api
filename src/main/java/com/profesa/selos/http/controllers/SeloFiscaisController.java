package com.profesa.selos.http.controllers;

import com.profesa.selos.domain.entities.SelosFiscais;
import com.profesa.selos.services.SelosFiscaisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/selos")
@RequiredArgsConstructor
public class SeloFiscaisController {
    private SelosFiscaisService service;

    @PostMapping("/solicitar")
    public ResponseEntity<SelosFiscais> solicitar(@RequestBody SeloRequestDto data, @RequestHeader("X-User") String user){
        SelosFiscais selo = service.solicitarSelo(data.getEmpresaId(), data.getProduto(), user);

        return ResponseEntity.status(HttpStatus.CREATED).body(selo);
    }

    @PostMapping("/validar/{codigo}")
    public ResponseEntity<Void> validar(@PathVariable String codigo, @RequestHeader("X-User") String user){
        service.validarSelo(codigo, user);
        return ResponseEntity.ok().build();
    }
}
