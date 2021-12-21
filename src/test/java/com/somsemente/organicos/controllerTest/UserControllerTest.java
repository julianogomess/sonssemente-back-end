package com.somsemente.organicos.controllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.somsemente.organicos.Utils;
import com.somsemente.organicos.config.jwtConfig.JwtTokenProvider;
import com.somsemente.organicos.model.AuthBody;
import com.somsemente.organicos.model.User;
import com.somsemente.organicos.repository.UserRepository;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public CustomUserService service;

    @Autowired
    UserRepository ur;

    Utils u = new Utils();

    @Test
    void loginSucess() throws Exception {
        AuthBody auth = new AuthBody();
        auth.setEmail("abc@ibm.com");
        auth.setPassword("abc123");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                        .content(objectMapper.writeValueAsString(auth))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();
        String resultCase = result.getResponse().getContentAsString();
        Assertions.assertNotNull(resultCase);
        Assertions.assertTrue(resultCase.contains("abc@ibm.com"));
    }
    @Test
    void loginError() throws Exception {
        AuthBody auth = new AuthBody();
        auth.setEmail("abc@ibm.com");
        auth.setPassword("abc12");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                        .content(objectMapper.writeValueAsString(auth))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
        String resultCase = result.getResponse().getContentAsString();
        Assertions.assertNotNull(resultCase);
        System.out.println(resultCase);
        //Assertions.assertTrue(resultCase.contains("Senha Invalida"));
    }

    @Test
    void cadastro() throws Exception {
        User user = u.user();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/cadastro")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        String resultCase = result.getResponse().getContentAsString();
        Assertions.assertNotNull(resultCase);
        Assertions.assertTrue(resultCase.contains(user.getNome()));
        service.deleteByCpf(user.getCpf());
    }

    @Test
    void getAll() throws Exception{
        String token = jwtTokenProvider.createToken("abc@ibm.com",ur.findByEmail("abc@ibm.com").getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/listatodos")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isFound()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertTrue(resultString.contains("abc@ibm.com"));
    }

    @Test
    void getByCpf() throws Exception {
        User user = ur.findByEmail("abc@ibm.com");
        String token = jwtTokenProvider.createToken(user.getEmail(),user.getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/buscaporcpf/"+user.getCpf())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isFound()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertTrue(resultString.contains(user.getEmail()));
    }

    @Test
    void deleteByCpf() throws Exception{
        User user = u.user();
        service.save(user);
        String token = jwtTokenProvider.createToken(user.getEmail(),user.getRoles());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/"+user.getCpf())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).andReturn();
        String resultString = result.getResponse().getContentAsString();
        Assertions.assertTrue(resultString.contains("Cliente Deletado!"));
    }
}