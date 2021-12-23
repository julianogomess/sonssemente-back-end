package com.somsemente.organicos.repository;

import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.utils.Tipo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
    List<Produto> findByTipo(Tipo tipo);
    Produto findByNome(String nome);
}
