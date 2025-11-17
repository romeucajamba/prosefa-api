package com.profesa.selos.utils;

import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.Year;

@Component
public class CodigoSeloGenerator {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public String nextCodigo(){
        //tabela counter com single row
        Long seq = (Long) em.createNativeQuery(
                "UPDATE selo_seq SET value = value + 1 RETURNING value"
        ).getSingleResult();

        int year = Year.now().getValue();

        return String.format("PROFESA-%d-%06d", year, seq);
    }
}
