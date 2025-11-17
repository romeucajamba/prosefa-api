package com.profesa.selos.services;

import com.profesa.selos.domain.entities.Empresa;
import com.profesa.selos.domain.entities.SelosFiscais;
import com.profesa.selos.domain.repositories.EmpresaRepository;
import com.profesa.selos.services.AuditoriaService;
import com.profesa.selos.domain.repositories.SelosFiscaisRepository;
import com.profesa.selos.utils.CodigoSeloGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SelosFiscaisService {
    private SelosFiscaisRepository seloRepo;
    private EmpresaRepository empresaRepo;
    private AuditoriaService auditoria;
    private CodigoSeloGenerator codigoGenerator;

    @Transactional
    public SelosFiscais solicitarSelo(UUID empresaId, String produto, String usuario){
        Empresa empresa = empresaRepo.findById(empresaId)
                .orElseThrow(()-> new EntityNotFoundException("Empresa não encontrada"));

        if (empresa.getStatus() != Empresa.StatusEmpresa.ATIVA){
            throw new IllegalStateException("Empresa não está ATIVA");
        };

        //Bloquear se houver selos não válidos abixo de 30 dias
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);

        Long pendentes = seloRepo.findByEmpresaAndEstadoAndDataEmissaoBefore(
                empresa, SelosFiscais.EstadoSelo.PENDENTE, threshold
        ).size();

        if(pendentes > 0){
            throw new IllegalStateException("Empresa possui selos pendentes há mais d 30 dias");
        }

        SelosFiscais selo = SelosFiscais.builder()
                .empresa(empresa)
                .produto(produto)
                .estado(SelosFiscais.EstadoSelo.EMITIDO)
                .dataEmissao(LocalDateTime.now())
                .codigo(codigoGenerator.nextCodigo())
                .build();

        SelosFiscais salvo = seloRepo.save(selo);

        auditoria.log("SeloFiscal", "SOLICITACAO_EMITIDA", usuario, "selo:" + salvo.getCodigo());
        return salvo;
    }

    @Transactional
    public void validarSelo(String codigo, String usuario){
        SelosFiscais selo = seloRepo.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("selo não encontrado"));

        if(selo.getEstado() == SelosFiscais.EstadoSelo.VALIDADO){
            throw new IllegalStateException("selo já foi validado");
        };

        selo.setEstado(SelosFiscais.EstadoSelo.VALIDADO);
        seloRepo.save(selo);
        auditoria.log("SeloFiscal", "Validacao_REALIZADA", usuario, "selo:" + selo.getCodigo());

    }
}
