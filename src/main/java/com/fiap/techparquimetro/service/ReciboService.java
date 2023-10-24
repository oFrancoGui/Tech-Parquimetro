package com.fiap.techparquimetro.service;

import com.fiap.techparquimetro.model.Recibo;

import java.util.List;

public interface ReciboService {
    public List<Recibo> onterTodos();

    public Recibo criar(Recibo recibo);

    public Recibo obterPorCodigo(String codigo);

    //Metodos e assinaturas para serem implementados
}
