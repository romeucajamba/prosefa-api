package com.profesa.selos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record SelosDto(
        @NotNull UUID empresaId,
        @NotBlank String produto
) {
}
