package com.somsemente.organicos.repository;

import com.somsemente.organicos.model.Pagamento;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PagamentoRepository extends MongoRepository<Pagamento,String> {
    @Aggregation(pipeline = {"{$group: { _id: '', total: {$sum: $valor}}}"})
    public double sumValor();

    @Query("{dataVenc:{$gt:?0}}")
    List<Pagamento> findPagamentoAberto(Date date);

    @Aggregation(pipeline = {"{$match: {dataVenc:{$gt:?0}}}","{$group: { _id: '', total: {$sum: $valor}}}"})
    double sumValoremAberto(Date date);
}
