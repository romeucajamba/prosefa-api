package com.profesa.selos.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "selos_fiscais", uniqueConstraints = @UniqueConstraint(columnNames = "codigo"))
public class SelosFiscais {
    @Id@GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    private String produto;

    private LocalDateTime dataEmissao;

    @Enumerated(EnumType.STRING)
    private EstadoSelo estado;

    public enum EstadoSelo {
        PENDENTE,
        EMITIDO,
        VALIDADO,
        INVALIDADO
    }

}
