package com.profesa.selos.domain.entities;

import com.profesa.selos.domain.enums.EstadoSelo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SelosFiscais {
    @Id@GeneratedValue
    private UUID id;
    @Column(unique = true, nullable = false)
    private String codigo;

    @ManyToOne
    private Empresa empresa;

    private String produto;

    private LocalDateTime dataEmissao;

    @Enumerated(EnumType.STRING)
    private EstadoSelo estado;
}
