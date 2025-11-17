package com.profesa.selos.domain.entities;

import com.profesa.selos.domain.enums.StatusEmpresa;
import com.profesa.selos.domain.enums.TipoEmpresa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Empresa {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String nif;

    private  String nome;

    @Enumerated(EnumType.STRING)
    private TipoEmpresa tipo;

    @Enumerated(EnumType.STRING)
    private StatusEmpresa status;

    private LocalDateTime dataRegistro;
}
