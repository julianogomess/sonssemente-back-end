package com.somsemente.organicos.controllerTest;

import com.somsemente.organicos.controller.ScheduleController;
import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleControllerTest {

    @Autowired
    ScheduleController controller;

    @Autowired
    ProdutoService produtoService;

    @Test
    void emailToClientes() {
        controller.emailToClientes();
    }


    @Test
    void pedidosDeVinho() {
        if(produtoService.findByNome("Vinho Branco")==null && produtoService.findByNome("Vinho Tinto")==null){
            controller.pedidosDeVinho();
            Assertions.assertNotNull(produtoService.findByNome("Vinho Branco"));
            Assertions.assertNotNull(produtoService.findByNome("Vinho Tinto"));
            produtoService.deleteByNome("Vinho Branco");
            produtoService.deleteByNome("Vinho Tinto");
        }

    }

    @Test
    void apagarpedidosDeVinho() {
        if(produtoService.findByNome("Vinho Branco")==null && produtoService.findByNome("Vinho Tinto")==null){
            controller.pedidosDeVinho();
            controller.ApagarpedidosDeVinho();
            Assertions.assertNull(produtoService.findByNome("Vinho Branco"));
            Assertions.assertNull(produtoService.findByNome("Vinho Tinto"));
        }
    }
}