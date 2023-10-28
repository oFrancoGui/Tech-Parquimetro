package com.fiap.techparquimetro.service;

import com.fiap.techparquimetro.model.Recibo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReciboService {
    public List<Recibo> onterTodos();

    public Recibo criarRecibo(String idVeiculo);
    public ResponseEntity<?> finalizaRecibo(String idRecibo);

    public Recibo obterPorCodigo(String codigo);

    //Metodos e assinaturas para serem implementados
}
