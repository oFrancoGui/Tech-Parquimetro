package com.fiap.techparquimetro.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalTime;

@Document
@Data
public class Recibo {
    @Id
    private String codigo;
    private Veiculo veiculo;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private BigDecimal valorRecibo;
}
