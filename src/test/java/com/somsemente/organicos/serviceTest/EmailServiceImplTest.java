package com.somsemente.organicos.serviceTest;

import com.somsemente.organicos.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmailServiceImplTest {

    @Autowired
    EmailService service;
    @Test
    void sendSimpleMessage() {
        service.sendSimpleMessage("sonssementeteste@gmail.com", "Teste","Teste");
    }
}