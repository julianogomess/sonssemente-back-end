package com.somsemente.organicos;

import com.somsemente.organicos.model.*;
import com.somsemente.organicos.model.utils.Tipo;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {
    public Fornecedor fornecedor(){
        Fornecedor f = new Fornecedor();
        f.setEmail("123@ibm.com");
        f.setCnpj("86889939000174");
        f.setEndereco(this.endereco());
        f.setNome("Casa de Legumes");
        f.setTelefone("14999091234");
        return f;
    }

    public Endereco endereco(){
        Endereco e = new Endereco();
        e.setCep("0760123");
        e.setBairro("Santa Ines");
        e.setCidade("Mairiporã");
        e.setNumero("10");
        e.setRua("Rua Paulo Soares");
        e.setUf("SP");
        return e;
    }

    public Produto produto(){
        Produto p = new Produto();
        p.setFornecedor(this.fornecedor());
        p.setNome("Cebola Roxa");
        p.setPreco(2.0);
        p.setTipo(Tipo.Verdura);
        return p;
    }

    public Produto produto2(){
        Produto p = new Produto();
        p.setFornecedor(this.fornecedor());
        p.setNome("Abacaxi");
        p.setPreco(4.0);
        p.setTipo(Tipo.Fruta);
        return p;
    }

    public User user(){
        User u = new User();
        u.setNome("Livia");
        u.setCpf("60777916010");
        u.setEmail("qwe@ibm.com");
        u.setDataNasc("23-12-1998");
        u.setPassword("123455");
        u.setTelefone("1199901221");
        u.setEndereco(this.endereco());
        return u;
    }

    public Pedido pedido(){
        Pedido p = new Pedido();
        p.setCliente(this.user());
        List<ItemPedido> lista = new ArrayList<>();
        lista.add(new ItemPedido(this.produto(),5.0));
        lista.add(new ItemPedido(this.produto2(),5.0));
        p.setLista(lista);
        return p;
    }

    public List<ItemPedido> lista(Produto p1, Produto p2){
        List<ItemPedido> lista = new ArrayList<>();
        lista.add(new ItemPedido(p1,5.0));
        lista.add(new ItemPedido(p2,5.0));
        return lista;
    }

    public Pagamento pagamento(){
        Pagamento p = new Pagamento();
        p.setValor(550.0);
        Date hoje = new Date();
        Date data = new Date(hoje.getTime() + (1000 * 60 * 60 * 24));
        p.setDataVenc(data);
        p.setVeiculo("Credito");
        return p;
    }
    public Historico historico(){
        Historico h = new Historico();
        h.setPesquisa("Banana");
        h.setRealizado(false);
        h.setEmail("abc@ibm.com");
        return h;
    }
}
