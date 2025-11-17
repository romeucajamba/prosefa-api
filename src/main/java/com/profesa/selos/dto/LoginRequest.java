package com.profesa.selos.dto;

public record LoginRequest(
        String email,
        String senha
) {
}