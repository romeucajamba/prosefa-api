package com.profesa.selos.dto;
import jakarta.validation.Valid;

@Valid
public record RegisterRequest(
        String email,
        String senha
) {
}
