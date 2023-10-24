package com.fiap.techparquimetro.service.impl;

import com.fiap.techparquimetro.model.Recibo;
import com.fiap.techparquimetro.repository.ReciboRepository;
import com.fiap.techparquimetro.service.ReciboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReciboServiceImpl implements ReciboService {

    @Autowired
    private ReciboRepository reciboRepository;

    @Override
    public List<Recibo> onterTodos() {
        return this.reciboRepository.findAll();
    }

    @Override
    public Recibo criar(Recibo recibo) {
        return this.reciboRepository.save(recibo);
    }

    @Override
    public Recibo obterPorCodigo(String codigo) {
        return this.reciboRepository
                .findById(codigo)
                .orElseThrow(()-> new IllegalArgumentException("Artigo nao existente"));
    }
}
