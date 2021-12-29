package com.somsemente.organicos.serviceTest;

import com.somsemente.organicos.Utils;
import com.somsemente.organicos.model.*;
import com.somsemente.organicos.service.FornecedorService;
import com.somsemente.organicos.service.PedidoService;
import com.somsemente.organicos.service.ProdutoService;
import com.somsemente.organicos.service.impl.CustomUserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PedidoServiceImplTest {

    @Autowired
    PedidoService pedidoService;
    @Autowired
    CustomUserService userService;
    @Autowired
    ProdutoService produtoService;
    @Autowired
    FornecedorService fornecedorService;

    Utils u = new Utils();


    @Test
    void delete() {
        User user = userService.save(u.user());
        Fornecedor fornecedor = fornecedorService.save(u.fornecedor());
        Produto p1 = produtoService.save(u.produto(),fornecedor.getCnpj());
        Produto p2 = produtoService.save(u.produto2(),fornecedor.getCnpj());
        List<ItemPedido> lista = u.lista(p1,p2);
        Pedido p = pedidoService.save(lista,user);
        Assertions.assertNotNull(pedidoService.findById(p.getId()));
        pedidoService.delete(p);
        Assertions.assertNull(pedidoService.findById(p.getId()));
        fornecedorService.deleteByCnpj(fornecedor.getCnpj());
        userService.deleteByCpf(user.getCpf());
        produtoService.deleteById(p1.getId());
        produtoService.deleteById(p2.getId());
    }

    @Test
    void findByUser() {
        User user = userService.save(u.user());
        Assertions.assertTrue(pedidoService.findByUser(user.getCpf()).isEmpty());
        Fornecedor fornecedor = fornecedorService.save(u.fornecedor());
        Produto p1 = produtoService.save(u.produto(),fornecedor.getCnpj());
        Produto p2 = produtoService.save(u.produto2(),fornecedor.getCnpj());
        List<ItemPedido> lista = u.lista(p1,p2);
        Pedido p = pedidoService.save(lista,user);
        //teste
        Assertions.assertNotNull(pedidoService.findByUser(user.getCpf()));
        fornecedorService.deleteByCnpj(fornecedor.getCnpj());
        userService.deleteByCpf(user.getCpf());
        produtoService.deleteById(p1.getId());
        produtoService.deleteById(p2.getId());
        pedidoService.delete(p);
    }

    @Test
    void findById() {
        User user = userService.save(u.user());
        Assertions.assertTrue(pedidoService.findByUser(user.getCpf()).isEmpty());
        Fornecedor fornecedor = fornecedorService.save(u.fornecedor());
        Produto p1 = produtoService.save(u.produto(),fornecedor.getCnpj());
        Produto p2 = produtoService.save(u.produto2(),fornecedor.getCnpj());
        List<ItemPedido> lista = u.lista(p1,p2);
        Pedido p = pedidoService.save(lista,user);
        //teste
        Assertions.assertNotNull(pedidoService.findById(p.getId()));
        fornecedorService.deleteByCnpj(fornecedor.getCnpj());
        userService.deleteByCpf(user.getCpf());
        produtoService.deleteById(p1.getId());
        produtoService.deleteById(p2.getId());
        pedidoService.delete(p);
    }

    @Test
    void findAll() {
        List<Pedido> lista = pedidoService.findAll();
        Assertions.assertFalse(lista.isEmpty());
        Assertions.assertTrue(lista.get(0).getId().equals("61c9cea75628416c21bde328"));
    }

    @Test
    void findNaoFinalizado() {
        List<Pedido> lista = pedidoService.findNaoFinalizado();
        Assertions.assertFalse(lista.isEmpty());
        Assertions.assertTrue(lista.get(0).getId().equals("61c9d509eddbab1a393b6f6d"));
    }

    @Test
    void save() {
        User user = userService.save(u.user());
        Assertions.assertTrue(pedidoService.findByUser(user.getCpf()).isEmpty());
        Fornecedor fornecedor = fornecedorService.save(u.fornecedor());
        Produto p1 = produtoService.save(u.produto(),fornecedor.getCnpj());
        Produto p2 = produtoService.save(u.produto2(),fornecedor.getCnpj());
        List<ItemPedido> lista = u.lista(p1,p2);
        Assertions.assertTrue(pedidoService.findByUser(user.getCpf()).isEmpty());
        Pedido p = pedidoService.save(lista,user);
        Assertions.assertFalse(pedidoService.findByUser(user.getCpf()).isEmpty());
        Assertions.assertEquals(p.getValor(),30);
        fornecedorService.deleteByCnpj(fornecedor.getCnpj());
        userService.deleteByCpf(user.getCpf());
        produtoService.deleteById(p1.getId());
        produtoService.deleteById(p2.getId());
        pedidoService.delete(p);
    }

    @Test
    void updateStatus() {
        User user = userService.save(u.user());
        Assertions.assertTrue(pedidoService.findByUser(user.getCpf()).isEmpty());
        Fornecedor fornecedor = fornecedorService.save(u.fornecedor());
        Produto p1 = produtoService.save(u.produto(),fornecedor.getCnpj());
        Produto p2 = produtoService.save(u.produto2(),fornecedor.getCnpj());
        List<ItemPedido> lista = u.lista(p1,p2);
        Pedido p = pedidoService.save(lista,user);
        Assertions.assertFalse(pedidoService.findById(p.getId()).isFinalizado());
        pedidoService.updateStatus(p);
        Assertions.assertTrue(pedidoService.findById(p.getId()).isFinalizado());
        fornecedorService.deleteByCnpj(fornecedor.getCnpj());
        userService.deleteByCpf(user.getCpf());
        produtoService.deleteById(p1.getId());
        produtoService.deleteById(p2.getId());
        pedidoService.delete(p);

    }

    @Test
    void updatePedido() {
        User user = userService.save(u.user());
        Assertions.assertTrue(pedidoService.findByUser(user.getCpf()).isEmpty());
        Fornecedor fornecedor = fornecedorService.save(u.fornecedor());
        Produto p1 = produtoService.save(u.produto(),fornecedor.getCnpj());
        Produto p2 = produtoService.save(u.produto2(),fornecedor.getCnpj());
        List<ItemPedido> lista = u.lista(p1,p2);
        Pedido p = pedidoService.save(lista,user);
        Assertions.assertEquals(pedidoService.findById(p.getId()).getValor(),30);
        p.setValor(40.0);
        pedidoService.updatePedido(p);
        Assertions.assertEquals(pedidoService.findById(p.getId()).getValor(),40);
        fornecedorService.deleteByCnpj(fornecedor.getCnpj());
        userService.deleteByCpf(user.getCpf());
        produtoService.deleteById(p1.getId());
        produtoService.deleteById(p2.getId());
        pedidoService.delete(p);
    }
}