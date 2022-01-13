package com.somsemente.organicos.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somsemente.organicos.Utils;
import com.somsemente.organicos.config.jwtConfig.JwtTokenProvider;
import com.somsemente.organicos.model.Pagamento;
import com.somsemente.organicos.repository.UserRepository;
import com.somsemente.organicos.service.PagamentoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PagamentoService service;

    @Autowired
    UserRepository ur;

    Utils u = new Utils();

    @Test
    void getPagamentos() throws Exception{
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/pagamentos/listatodos")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getById() throws Exception{
        Pagamento pTeste = u.pagamento();
        pTeste = service.save(pTeste);
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/pagamentos/buscaporid/"+pTeste.getId())
                .header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andReturn();
        service.deleteById(pTeste.getId());
    }

    @Test
    void cadastroPagamento() throws Exception{
        Pagamento pTeste = u.pagamento();
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/pagamentos/cadastro")
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(pTeste))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        String id = resultString.substring(7,31);
        service.deleteById(id);
    }

    @Test
    void deletePagamento() throws Exception{
        Pagamento pTeste = u.pagamento();
        pTeste = service.save(pTeste);
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/pagamentos/deleteporid/"+pTeste.getId())
                .header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andReturn();
    }

    @Test
    void getAbertos() throws Exception{
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/pagamentos/buscaporabertos")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getsoma() throws Exception{
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/pagamentos/somaabertos")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();
    }
}