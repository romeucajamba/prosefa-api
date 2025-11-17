package com.profesa.selos.dto;

import com.profesa.selos.domain.entities.Empresa;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmpresaResponse(
        UUID id,
        String nome,
        String nif,
        Empresa.TipoEmpresa tipo,
        Empresa.StatusEmpresa status,
        LocalDateTime dataRegistro
) {
}
