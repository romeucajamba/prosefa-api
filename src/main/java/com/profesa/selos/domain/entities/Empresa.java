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
@Data
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

    public static Snippet builder() {
        return null;
    }

    public UUID getId() {
        return null;
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
