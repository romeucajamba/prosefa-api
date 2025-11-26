package com.profesa.selos.dto;
import jakarta.validation.Valid;

@Valid
public record LoginRequest(
        String email,
        String senha
) {
}