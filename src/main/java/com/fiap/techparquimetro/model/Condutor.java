package com.fiap.techparquimetro.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class Condutor {
    @Id
    private String idCondutor;
    private String cpf;
    private String email;
    private String telefone;
    private FormaDePagamento formaDePagamento;
    @DBRef
    private Veiculo veiculo;


}
