package com.profesa.selos.dto;

import com.profesa.selos.domain.entities.Empresa;

public record EmpresaUpdateDto(
        Empresa.StatusEmpresa status
) {
}
