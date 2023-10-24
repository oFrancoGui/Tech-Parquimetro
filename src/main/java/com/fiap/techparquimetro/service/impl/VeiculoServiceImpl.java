package com.fiap.techparquimetro.service.impl;

import com.fiap.techparquimetro.model.Veiculo;
import com.fiap.techparquimetro.repository.VeiculoRepository;
import com.fiap.techparquimetro.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoServiceImpl implements VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Override
    public List<Veiculo> onterTodos() {
        return null;
    }

    @Override
    public Veiculo criar(Veiculo recibo) {
        return this.veiculoRepository.save(recibo);
    }
}
