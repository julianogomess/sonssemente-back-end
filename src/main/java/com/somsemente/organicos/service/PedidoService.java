package com.somsemente.organicos.service;

import com.somsemente.organicos.model.ItemPedido;
import com.somsemente.organicos.model.Pedido;
import com.somsemente.organicos.model.User;

import java.util.List;

public interface PedidoService {
    void delete(Pedido pedido);
    List<Pedido> findByUser(String cpf);
    Pedido findById(String id);
    List<Pedido> findAll();
    List<Pedido> findNaoFinalizado();
    Pedido save(List<ItemPedido> items, User user);
    Pedido updateStatus(Pedido pedido);
    Pedido updatePedido(Pedido pedido);
}
