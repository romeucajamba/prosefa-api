package com.profesa.selos.domain.entities;
import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "empresa", uniqueConstraints = @UniqueConstraint(columnNames = "nif"))
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


    public UUID getId() {
        return this.id;
    }

    public enum TipoEmpresa {
        FABRICANTE,
        IMPORTADOR
    }

    public enum StatusEmpresa {
        ATIVA,
        SUSPENSA,
        BLOQUEADA
    }

}
