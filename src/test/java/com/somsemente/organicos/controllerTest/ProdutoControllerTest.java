package com.somsemente.organicos.controllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.somsemente.organicos.Utils;
import com.somsemente.organicos.config.jwtConfig.JwtTokenProvider;
import com.somsemente.organicos.model.Fornecedor;
import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.repository.UserRepository;
import com.somsemente.organicos.service.FornecedorService;
import com.somsemente.organicos.service.ProdutoService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    ProdutoService service;

    @Autowired
    FornecedorService fornecedorService;

    @Autowired
    UserRepository ur;

    Utils u = new Utils();

    @Test
    void getProdutos() throws Exception {
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/produtos/listatodos")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isFound()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertTrue(resultString.contains("Batata"));
    }

    @Test
    void getProdutoPorTipo() throws Exception {
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/produtos/buscaportipo/Legume")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isFound()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertTrue(resultString.contains("Legume"));
    }

    @Test
    void cadastroProduto() throws Exception {
        Produto p = u.produto();
        fornecedorService.save(p.getFornecedor());
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/produtos/cadastro/"+p.getFornecedor().getCnpj())
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(p))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        fornecedorService.deleteByCnpj(p.getFornecedor().getCnpj());
        Assertions.assertTrue(resultString.contains(p.getNome()));
        List<Produto> lista = service.findByTipo(p.getTipo());
        for (Produto i : lista){
            if(i.getNome().equals(p.getNome())){
                service.deleteById(i.getId());
            }
        }

    }

    @Test
    void deleteById() throws Exception {
        Produto p = u.produto();
        fornecedorService.save(p.getFornecedor());
        p = service.save(p,p.getFornecedor().getCnpj());
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/produtos/deleteporid/"+p.getId())
                .header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertTrue(resultString.contains("Produto"));
        fornecedorService.deleteByCnpj(p.getFornecedor().getCnpj());
    }
}
