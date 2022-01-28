package com.somsemente.organicos.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somsemente.organicos.Utils;
import com.somsemente.organicos.config.jwtConfig.JwtTokenProvider;
import com.somsemente.organicos.model.*;
import com.somsemente.organicos.repository.UserRepository;
import com.somsemente.organicos.service.FornecedorService;
import com.somsemente.organicos.service.PedidoService;
import com.somsemente.organicos.service.ProdutoService;
import com.somsemente.organicos.service.impl.CustomUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository ur;

    @Autowired
    ProdutoService produtoService;
    @Autowired
    CustomUserService userService;
    @Autowired
    FornecedorService fornecedorService;
    @Autowired
    PedidoService pedidoService;

    Utils u = new Utils();



    @Test
    void getPedidos() throws Exception {
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/pedidos/listatodos")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getPedidoPorUsuario() throws Exception {
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/pedidos/buscaporuser/49507369813")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getPedidosAbertos() throws Exception {
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/pedidos/buscaabertos")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void cadastroPedido() throws Exception{
        User user = userService.save(u.user());
        Fornecedor fornecedor = fornecedorService.save(u.fornecedor());
        Produto p1 = produtoService.save(u.produto(),fornecedor.getCnpj());
        Produto p2 = produtoService.save(u.produto2(),fornecedor.getCnpj());
        List<ItemPedido> lista = u.lista(p1,p2);
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/pedidos/cadastro/"+user.getEmail())
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(lista))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        Assertions.assertFalse(pedidoService.findByUser(user.getCpf()).isEmpty());
        List<Pedido> l = pedidoService.findByUser(user.getCpf()) ;
        pedidoService.delete(l.get(0));
        fornecedorService.deleteByCnpj(fornecedor.getCnpj());
        userService.deleteByCpf(user.getCpf());
        produtoService.deleteById(p1.getId());
        produtoService.deleteById(p2.getId());

    }

    @Test
    void deletePedido() throws Exception {
        User user = userService.save(u.user());
        Fornecedor fornecedor = fornecedorService.save(u.fornecedor());
        Produto p1 = produtoService.save(u.produto(),fornecedor.getCnpj());
        Produto p2 = produtoService.save(u.produto2(),fornecedor.getCnpj());
        List<ItemPedido> lista = u.lista(p1,p2);
        Pedido p = pedidoService.save(lista,user);
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/pedidos/delete/"+p.getId())
                .header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        fornecedorService.deleteByCnpj(fornecedor.getCnpj());
        userService.deleteByCpf(user.getCpf());
        produtoService.deleteById(p1.getId());
        produtoService.deleteById(p2.getId());
    }


    @Test
    void atualizarPedido() throws Exception {
        User user = userService.save(u.user());
        Fornecedor fornecedor = fornecedorService.save(u.fornecedor());
        Produto p1 = produtoService.save(u.produto(),fornecedor.getCnpj());
        Produto p2 = produtoService.save(u.produto2(),fornecedor.getCnpj());
        List<ItemPedido> lista = u.lista(p1,p2);
        Pedido p = pedidoService.save(lista,user);
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        p.setValor(49.0);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/pedidos/atualizar/"+p.getId())
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(lista))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        fornecedorService.deleteByCnpj(fornecedor.getCnpj());
        userService.deleteByCpf(user.getCpf());
        produtoService.deleteById(p1.getId());
        produtoService.deleteById(p2.getId());
        pedidoService.delete(p);
    }

}