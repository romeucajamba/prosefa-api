package com.profesa.selos.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.profesa.selos.domain.entities.LogAuditoria;

import java.util.UUID;

public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, UUID>  {}
