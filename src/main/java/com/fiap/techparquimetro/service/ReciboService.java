package com.fiap.techparquimetro.service;

import com.fiap.techparquimetro.model.Recibo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReciboService {
    public List<Recibo> obterTodos();

    public Recibo criarRecibo(String idVeiculo);
    public ResponseEntity<?> finalizaRecibo(String idRecibo);

    public Recibo listarReciboPorCodigo(String codigo);

    //Metodos e assinaturas para serem implementados
}
