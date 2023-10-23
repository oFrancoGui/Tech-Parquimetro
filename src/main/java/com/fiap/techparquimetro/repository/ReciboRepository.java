package com.fiap.techparquimetro.repository;

import com.fiap.techparquimetro.model.Recibo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReciboRepository extends MongoRepository<Recibo, String> {
}
