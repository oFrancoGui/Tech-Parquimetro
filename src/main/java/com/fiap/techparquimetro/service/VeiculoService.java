package com.fiap.techparquimetro.service;


import com.fiap.techparquimetro.model.Veiculo;

import java.util.List;

public interface VeiculoService {
    public List<Veiculo> onterTodos();

    public Veiculo criar(Veiculo veiculo);

    List<Veiculo> findAll();

    public Veiculo obterVeiculoPorCodigo(String codigo);
}
