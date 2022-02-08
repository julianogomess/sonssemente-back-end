package com.somsemente.organicos.service;

import com.somsemente.organicos.dto.ItemPedidoDTO;
import com.somsemente.organicos.model.ItemPedido;
import com.somsemente.organicos.model.Pedido;
import com.somsemente.organicos.model.User;
import com.somsemente.organicos.model.utils.StatusPedido;

import java.util.List;

public interface PedidoService {
    boolean delete(Pedido pedido);
    List<Pedido> findByUser(String cpf);
    Pedido findById(String id);
    List<Pedido> findAll();
    List<Pedido> findNaoFinalizado();
    Pedido save(List<ItemPedido> items, User user);
    Pedido updateStatus(Pedido pedido, StatusPedido status);
    Pedido updatePedido(Pedido pedido);
    void mailStatus(Pedido pedido);
    void mailCadastro(Pedido pedido);
    void mailVendas();
    List<ItemPedido> transformarDTO(List<ItemPedidoDTO> lista);
    void atualizarEstoque(List<ItemPedido> items);
}
