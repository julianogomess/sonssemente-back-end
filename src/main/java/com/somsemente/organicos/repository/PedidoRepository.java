package com.somsemente.organicos.repository;

import com.somsemente.organicos.model.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido,String> {
}
