package com.profesa.selos.domain.repositories;

import com.profesa.selos.domain.entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Usuarios, UUID> {
}
