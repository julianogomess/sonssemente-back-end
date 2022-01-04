package com.somsemente.organicos.repository;

import com.somsemente.organicos.model.Pedido;
import com.somsemente.organicos.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends MongoRepository<Pedido,String> {
    List<Pedido> findByCliente(User user);

    Optional<Pedido> findById(String id);

    @Query("{finalizado:?0}")
    List<Pedido> getPedidosByFinalizado(Boolean finalizado);
}
