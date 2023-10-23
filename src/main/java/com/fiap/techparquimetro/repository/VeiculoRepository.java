package com.fiap.techparquimetro.repository;

import com.fiap.techparquimetro.model.Veiculo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VeiculoRepository extends MongoRepository<Veiculo, String> {
}
