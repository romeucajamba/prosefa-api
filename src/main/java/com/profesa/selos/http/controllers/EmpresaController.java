package com.profesa.selos.http.controllers;
import com.profesa.selos.domain.entities.Empresa;
import com.profesa.selos.domain.entities.SelosFiscais;
import com.profesa.selos.services.EmpresaService;
import com.profesa.selos.services.SelosFiscaisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/empresa")
@RequiredArgsConstructor
public class EmpresaController {
    private EmpresaService service;

    @PostMapping("/criar")
    public ResponseEntity<Empresa> criarEmpresa(@RequestBody EmpresaDto  data){
        Empresa interprise = service.criar(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(interprise);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarEmpresaPodId(@PathVariable UUID id){
        return ResponseEntity.of(service.findById(id));
    }
}
