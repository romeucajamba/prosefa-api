package com.profesa.selos.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class LogAuditoria {
    @Id
    @GeneratedValue
    private UUID id;

    private String entidade;
    private String acao;
    private String usuario;
    private LocalDateTime datHora;

    @Column(columnDefinition = "TEXT")
    private String detalhes;
}
