package com.somsemente.organicos.repository;

import com.somsemente.organicos.model.Historico;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoRepository extends MongoRepository<Historico,String> {
}
