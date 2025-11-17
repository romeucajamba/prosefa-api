package com.profesa.selos;

import com.profesa.selos.domain.entities.Empresa;
import com.profesa.selos.domain.repositories.EmpresaRepository;
import com.profesa.selos.domain.repositories.SelosFiscaisRepository;
import com.profesa.selos.services.AuditoriaService;
import com.profesa.selos.services.SelosFiscaisService;
import com.profesa.selos.utils.CodigoSeloGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SelosApplicationTests {
    @Mock
    SelosFiscaisRepository seloRepo;
    @Mock
    EmpresaRepository empresaRepo;
    @Mock
    AuditoriaService auditoria;
    @Mock
    CodigoSeloGenerator codigoGen;
    @InjectMocks
    SelosFiscaisService service;

    @Test
    void solicitarSelo_empresaInativa_deveLancar() {
        Empresa e = Empresa.builder().id(UUID.randomUUID()).status(Empresa.StatusEmpresa.SUSPENSA).build();
        when(empresaRepo.findById(any())).thenReturn(Optional.of(e));
        assertThrows(IllegalStateException.class, () -> service.solicitarSelo(e.getId(), "Produto", "user"));
    }
}