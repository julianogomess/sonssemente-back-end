package com.somsemente.organicos.service.impl;

import com.somsemente.organicos.model.ItemPedido;
import com.somsemente.organicos.model.Pedido;
import com.somsemente.organicos.model.User;
import com.somsemente.organicos.model.utils.StatusPedido;
import com.somsemente.organicos.repository.PedidoRepository;
import com.somsemente.organicos.service.EmailService;
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

    @Autowired
    EmailService emailService;

    @Override
    public boolean delete(Pedido pedido) {
        if(repository.findById(pedido.getId())==null){
            return false;
        }
        repository.delete(pedido);
        return true;
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
        pedido.setStatus(StatusPedido.Realizado);
        pedido = repository.save(pedido);
        return pedido;
    }


    @Override
    public Pedido updateStatus(Pedido pedido, StatusPedido status){
        pedido.setStatus(status);
        if (status.equals(StatusPedido.Entregue)){
            pedido.setFinalizado(true);
        }
        return repository.save(pedido);
    }


    @Override
    public Pedido updatePedido(Pedido pedido) {
        return repository.save(pedido);
    }

    @Override
    public void mailStatus(Pedido pedido) {
        String assunto = "O status do seu pedido foi atualizado!";
        String texto = "O status do pedido com id: " + pedido.getId() + " mudou para " + pedido.getStatus();
        if (pedido.getStatus().equals(StatusPedido.Separado)) {
            texto += "Dessa forma os produtos já estão conosco e na espera da entrega. \n Os itens: "+ formListItems(pedido)+ ". No valor de: " + pedido.getValor();
        }else if(pedido.getStatus().equals(StatusPedido.Entregue)) {
            texto += "Seu pedido foi entregue!!. \n Os itens: "+ formListItems(pedido)+ ". No valor de: " + pedido.getValor();
        }
        emailService.sendSimpleMessage(pedido.getCliente().getEmail(),assunto,texto);

    }

    @Override
    public void mailCadastro(Pedido pedido) {
        String assunto = "Pedido realizado com sucesso!";
        String texto = "O id do pedido é: " + pedido.getId() + " . \n E consta com esses produtos: " + formListItems(pedido);
        texto+= "\n Manteremos você informado das mudanças realizadas no pedido";
        emailService.sendSimpleMessage(pedido.getCliente().getEmail(),assunto,texto);
    }

    private Double calcValor(List<ItemPedido> items){
        try {
            Double soma = 0.0;
            for (ItemPedido i : items) {
                soma += i.getProduto().getPreco() * i.getQuantidade();
            }
            return soma;
        }catch (NullPointerException exception){
            throw new NullPointerException("Variavel mal escrita");
        }
    }

    private String formListItems(Pedido p){
        List<ItemPedido> lista = p.getLista();
        String msg = "[";
        for (ItemPedido i : lista){
            if (lista.lastIndexOf(i)== lista.size()-1){
                msg += i.getProduto().getNome()+ " - Quantidade: " + i.getQuantidade() + " ] ";
                break;
            }
            msg+= i.getProduto().getNome() + " - Quantidade: " + i.getQuantidade() + ", ";
        }
        return msg;
    }
}
