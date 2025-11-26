package com.profesa.selos.dto;
import lombok.Data;

@Data
public class Logs {
    private String entidade;
    private String acao;
    private String usuario;
    private String detalhes;
}
