package com.profesa.selos.dto;

import com.profesa.selos.domain.entities.Empresa;

public record EmpresaCreateDto(
        String nome,
        String nif,
        Empresa.TipoEmpresa tipo
){}