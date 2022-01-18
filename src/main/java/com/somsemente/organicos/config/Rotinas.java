package com.somsemente.organicos.config;

import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.utils.Tipo;
import com.somsemente.organicos.service.ProdutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class Rotinas {
    @Autowired
    ProdutoService produtoService;

    //Acontece as 8h30 na ultima terça do mês
    @Scheduled(cron = "0 30 8 ? * 3L")
    public void pedidosDeVinho() {
        log.info("Hoje é dia de colocar os vinhos no cardápio");
        log.info("A data é: " + new Date().toString());
        Produto produto = new Produto();
        produto.setNome("Vinho Branco");
        produto.setPreco(40.0);
        produto.setTipo(Tipo.Bebida);
        produtoService.save(produto,"07199027000102");
        Produto produto2 = new Produto();
        produto2.setNome("Vinho Tinto");
        produto2.setPreco(55.0);
        produto2.setTipo(Tipo.Bebida);
        produtoService.save(produto2,"07199027000102");
    }

    //Acontece as 8h30 na primeira terça de cada mes
    @Scheduled(cron = "0 30 8 ? * 3#1")
    public void ApagarpedidosDeVinho() {
        log.info("Hoje é dia de tirar os vinhos no cardápio");
        log.info("A data é: " + new Date().toString());
        produtoService.deleteByNome("Vinho Branco");
        produtoService.deleteByNome("Vinho Tinto");

    }


}