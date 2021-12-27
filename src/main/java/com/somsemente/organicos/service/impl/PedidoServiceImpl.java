package com.somsemente.organicos.service.impl;

import com.somsemente.organicos.model.ItemPedido;
import com.somsemente.organicos.model.Pedido;
import com.somsemente.organicos.model.User;
import com.somsemente.organicos.repository.PedidoRepository;
import com.somsemente.organicos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    PedidoRepository repository;

    @Autowired
    CustomUserService userService;

    @Override
    public void delete(Pedido pedido) {
        repository.delete(pedido);
    }

    @Override
    public List<Pedido> findByUser(String cpf) {
        return repository.findByCliente(userService.findByCpf(cpf));
    }

    @Override
    public Pedido findById(String id) {
        Optional<Pedido> pedido = repository.findById(id);
        if(!pedido.isPresent()){
            return null;
        }
        return pedido.get();
    }

    @Override
    public List<Pedido> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Pedido> findNaoFinalizado() {
        return repository.getPedidosByFinalizado(false);
    }

    @Override
    public Pedido save(List<ItemPedido> items, User user) {
        Pedido pedido = new Pedido();
        pedido.setCliente(user);
        pedido.setLista(items);
        pedido.setData(new Date());
        pedido.setFinalizado(false);
        pedido.setValor(calcValor(items));
        pedido = repository.save(pedido);
        return pedido;
    }

    @Override
    public Pedido update(Pedido pedido) {
        pedido.setFinalizado(true);
        return repository.save(pedido);
    }

    private Double calcValor(List<ItemPedido> items){
        Double soma = 0.0;
        for(ItemPedido i: items){
            soma += i.getProduto().getPreco()*i.getQuantidade();
        }
        return soma;
    }
}
