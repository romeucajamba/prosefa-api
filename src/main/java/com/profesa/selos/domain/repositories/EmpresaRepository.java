package com.profesa.selos.domain.repositories;

import com.profesa.selos.domain.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {
    Optional<Empresa> findByNif(String nif);
}
