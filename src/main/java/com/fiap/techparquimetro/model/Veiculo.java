package com.fiap.techparquimetro.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Veiculo {
    @Id
    private String idVeiculo;
    private String placa;
    private String modelo;
    private String marca;
}
