package com.profesa.selos.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
@Builder
@Setter
@Table(name = "log_auditoria")
public class LogAuditoria {
    @Id
    @GeneratedValue
    private UUID id;

    private String entidade;
    private String acao;
    private String usuario;
    private LocalDateTime dataHora;

    @Column(columnDefinition = "TEXT")
    private String detalhes;

}
