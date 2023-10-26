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
    public Veiculo criar(Veiculo veiculo) {
        return this.veiculoRepository.save(veiculo);
    }

    @Override
    public List<Veiculo> findAll() {
        return null;
    }

    @Override
    public Veiculo obterVeiculoPorCodigo(String codigo) {
        return this.veiculoRepository
                .findById(codigo)
                .orElseThrow(()-> new RuntimeException("Veiculo nao deu entrada"));
    }
}
