package com.profesa.selos.services;

import com.profesa.selos.domain.entities.LogAuditoria;
import com.profesa.selos.domain.repositories.LogAuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditoriaService {
    private LogAuditoriaRepository auditoriaRepo;

    public void log(
            String entidade,
            String acao,
            String usuario,
            String detalhes
    ){
        auditoriaRepo.save(LogAuditoria.builder()
                .entidade(entidade)
                .acao(acao)
                .usuario(usuario)
                .dataHora(LocalDateTime.now())
                .detalhes(detalhes)
                .build()
        );
    }
}
