package com.fiap.techparquimetro.repository;

import com.fiap.techparquimetro.model.Condutor;
import com.fiap.techparquimetro.model.Recibo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CondutorRepository extends MongoRepository<Condutor, String> {
}
