package com.profesa.selos.http.controllers;
import com.profesa.selos.dto.Logs;
import com.profesa.selos.services.AuditoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auditoria")
@RequiredArgsConstructor
public class LogAuditoriaController {
    private final AuditoriaService logsAuditoria;

    @PostMapping("/")
    ResponseEntity<String> registerLog(@RequestBody Logs data){
        logsAuditoria.log(
                data.getEntidade(),
                data.getAcao(),
                data.getUsuario(),
                data.getDetalhes()
        );

        return ResponseEntity.ok("Log registrado com sucesso.");
    }
}
