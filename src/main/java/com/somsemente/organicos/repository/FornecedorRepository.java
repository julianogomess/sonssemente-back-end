package com.somsemente.organicos.repository;

import com.somsemente.organicos.model.Fornecedor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FornecedorRepository extends MongoRepository<Fornecedor,String> {
    Fornecedor findByCnpj(String cnpj);
    void deleteByCnpj(String cnpj);
}
