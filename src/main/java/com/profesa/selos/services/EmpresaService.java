package com.profesa.selos.services;

import com.profesa.selos.domain.entities.Empresa;
import com.profesa.selos.domain.repositories.EmpresaRepository;
import com.profesa.selos.dto.EmpresaCreateDto;
import com.profesa.selos.exception.BusinessException;
import com.profesa.selos.dto.EmpresaResponse;
import com.profesa.selos.dto.EmpresaUpdateDto;
import com.profesa.selos.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Transactional
    public EmpresaResponse criar(EmpresaCreateDto dto) {

        if (empresaRepository.findByNif(dto.nif()).isPresent()) {
            throw new BusinessException("Já existe empresa com esse NIF.");
        }

        Empresa empresa = Empresa.builder()
                .nome(dto.nome())
                .nif(dto.nif())
                .tipo(dto.tipo())
                .status(Empresa.StatusEmpresa.ATIVA)
                .dataRegistro(LocalDateTime.now())
                .build();

        Empresa saved = empresaRepository.save(empresa);
        return toResponse(saved);
    }

    public EmpresaResponse buscar(UUID id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada"));
        return toResponse(empresa);
    }

    public List<EmpresaResponse> listarTodas() {
        return empresaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public EmpresaResponse atualizarStatus(UUID id, EmpresaUpdateDto dto) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Empresa não encontrada"));

        empresa.setStatus(dto.status());
        empresaRepository.save(empresa);

        return toResponse(empresa);
    }

    private EmpresaResponse toResponse(Empresa e) {
        return new EmpresaResponse(
                e.getId(),
                e.getNome(),
                e.getNif(),
                e.getTipo(),
                e.getStatus(),
                e.getDataRegistro()
        );
    }
}