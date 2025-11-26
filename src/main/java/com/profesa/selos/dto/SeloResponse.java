package com.profesa.selos.dto;
import com.profesa.selos.domain.entities.SelosFiscais;
import java.time.LocalDateTime;
import java.util.UUID;

public record SeloResponse(
        UUID id,
        String codigo,
        UUID empresaId,
        String produto,
        LocalDateTime dataEmissao,
        SelosFiscais.EstadoSelo estado
) {
    public static SeloResponse fromEntity(SelosFiscais s) {
        return new SeloResponse(
                s.getId(),
                s.getCodigo(),
                s.getEmpresa() != null ? s.getEmpresa().getId() : null,
                s.getProduto(),
                s.getDataEmissao(),
                s.getEstado()
        );
    }
}
