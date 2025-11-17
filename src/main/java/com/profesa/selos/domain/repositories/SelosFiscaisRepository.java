package com.profesa.selos.domain.repositories;
import com.profesa.selos.domain.entities.Empresa;
import com.profesa.selos.domain.entities.SelosFiscais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SelosFiscaisRepository extends JpaRepository<SelosFiscais, UUID>  {
    Optional<SelosFiscais> findByCodigo(String codigo);
    Long countByEmpresaAndEstado(Empresa empresa, SelosFiscais.EstadoSelo estado);
    List<SelosFiscais> findByEmpresaAndEstadoAndDataEmissaoBefore(Empresa empresa,SelosFiscais.EstadoSelo estado, LocalDateTime dateTime);

}
