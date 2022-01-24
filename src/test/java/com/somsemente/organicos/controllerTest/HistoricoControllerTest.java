package com.somsemente.organicos.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.somsemente.organicos.Utils;
import com.somsemente.organicos.config.jwtConfig.JwtTokenProvider;
import com.somsemente.organicos.dto.HistoricoDTO;
import com.somsemente.organicos.model.Historico;
import com.somsemente.organicos.repository.UserRepository;
import com.somsemente.organicos.service.HistoricoService;
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
class HistoricoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepository ur;

    @Autowired
    private HistoricoService service;
    Utils u = new Utils();

    @Test
    void getAll() throws Exception{
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/pesquisas/listatodos")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void salvarPesquisa() throws Exception{
        HistoricoDTO h = u.historicoDTO();
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/pesquisas/salvar")
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(h))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        int idloc = resultString.lastIndexOf("id");
        String id = resultString.substring(idloc+5,idloc+29);
        service.delete(service.findById(id));
    }

    @Test
    void deleteById() throws Exception {
        Historico h = u.historico();
        h = service.save(h);
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/pesquisas/deletar/"+h.getId())
                .header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andReturn();

    }
}