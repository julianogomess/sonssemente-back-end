package com.somsemente.organicos.utils;

import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.repository.ProdutoRepository;
import com.somsemente.organicos.service.ProdutoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.TimerTask;

@Slf4j
public class Tasks extends TimerTask {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ProdutoService produtoService;

    @Override
    public void run() {
        log.info("Rotina de enviar email");
        try {
            List<Produto> lista = produtoService.findAll();
            log.info(String.valueOf(lista));
            /*
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo("sonssementeteste@gmail.com");
            msg.setSubject("Testing from Spring Boot");
            msg.setText("Hello World \n Spring Boot Email");
            mailSender.send(msg);*/

        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        }
    }
}
