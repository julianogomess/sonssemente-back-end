package com.somsemente.organicos.repository;

import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.utils.Tipo;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
    List<Produto> findByTipo(Tipo tipo);
    Produto findByNome(String nome);
    void deleteByNome(String nome);
    @Query("{nome:{$regex:?0}}")
    List<Produto> pesquisPorNome(String regex);

    @Aggregation(pipeline = {"{$sample: { size: 5 }}"})
    List<Produto> getHome();
}
