package com.somsemente.organicos.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somsemente.organicos.config.jwtConfig.JwtTokenProvider;
import com.somsemente.organicos.model.Fornecedor;
import com.somsemente.organicos.repository.UserRepository;
import com.somsemente.organicos.Utils;
import com.somsemente.organicos.service.FornecedorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FornecedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    FornecedorService service;

    @Autowired
    UserRepository ur;

    Utils u = new Utils();

    @Test
    void getTodosF() throws Exception {
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/fornecedores/listatodos")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertTrue(resultString.contains("Fazenda Chico Bento"));
    }

    @Test
    void getByCnpj() throws Exception {
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/fornecedores/buscaporcnpj/07199027000102")
                .header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertTrue(resultString.contains("Fazenda Chico Bento"));
    }

    @Test
    void cadastroFornecedor() throws Exception {
        Fornecedor fornecedor = u.fornecedor();
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/fornecedores/cadastro")
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(fornecedor))
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertTrue(resultString.contains(fornecedor.getNome()));
        service.deleteByCnpj(fornecedor.getCnpj());
    }

    @Test
    void deleteByCnpj() throws Exception{
        Fornecedor fornecedor = u.fornecedor();
        service.save(fornecedor);
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/fornecedores/deleteporcnpj/"+fornecedor.getCnpj())
                .header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertTrue(resultString.contains("Fornecedor"));

    }

    @Test
    void badAuthenticationLogin() throws Exception{
        String tokenInvalid = "invalid token";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/fornecedores/listatodos"))
                .andExpect(status().isUnauthorized()).andReturn();

    }

    @Test
    void atualizarByCpnj() throws Exception{
        Fornecedor fornecedor = u.fornecedor();
        service.save(fornecedor);
        fornecedor.setNome("Fazenda dos Legumes");
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/fornecedores/atualizar/"+fornecedor.getCnpj())
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(fornecedor))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertTrue(resultString.contains(fornecedor.getNome()));
        service.deleteByCnpj(fornecedor.getCnpj());
    }
}
