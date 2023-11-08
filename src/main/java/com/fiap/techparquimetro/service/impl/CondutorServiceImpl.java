package com.fiap.techparquimetro.service.impl;

import com.fiap.techparquimetro.model.Condutor;
import com.fiap.techparquimetro.repository.CondutorRepository;
import com.fiap.techparquimetro.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CondutorServiceImpl implements CondutorService {
    @Autowired
    private CondutorRepository condutorRepository;


    @Override
    public Condutor listarCondutorPorCodigo(String idCondutor) {
        return this.condutorRepository
                .findById(idCondutor)
                .orElseThrow(()-> new IllegalArgumentException("Condutor não cadastrado!"));
    }
}
