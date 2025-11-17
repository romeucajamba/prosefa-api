package com.profesa.selos.dto;

import com.profesa.selos.domain.entities.Empresa;
import com.profesa.selos.domain.entities.SelosFiscais;

import java.time.LocalDateTime;
import java.util.UUID;

public record SelosDto(
        UUID id,
        String codigo,
        Empresa empresa,
        String produto,
        LocalDateTime dataEmissao,
        SelosFiscais.EstadoSelo estado
) {
}
