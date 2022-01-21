package com.somsemente.organicos.serviceTest;

import com.somsemente.organicos.Utils;
import com.somsemente.organicos.controller.ScheduleController;
import com.somsemente.organicos.model.Historico;
import com.somsemente.organicos.model.utils.HistHelper;
import com.somsemente.organicos.repository.HistoricoRepository;
import com.somsemente.organicos.service.EmailService;
import com.somsemente.organicos.service.HistoricoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HistoricoServiceImplTest {

    @Autowired
    HistoricoRepository repository;

    @Autowired
    HistoricoService service;

    Utils u = new Utils();
    @Test
    void findAll() {
        int countA = service.findAll().size();
        Historico h = u.historico();
        h = service.save(h);
        Assertions.assertEquals(countA+1,service.findAll().size());
        service.delete(h);
    }

    @Test
    void findById() {
        Historico h = u.historico();
        h = service.save(h);
        Historico teste = service.findById(h.getId());
        Assertions.assertEquals(teste.getPesquisa(),h.getPesquisa());
        service.delete(teste);
    }

    @Test
    void save() {
        Historico h = u.historico();
        h = service.save(h);
        Historico teste = service.findById(h.getId());
        Assertions.assertEquals(teste.getPesquisa(),h.getPesquisa());
        service.delete(teste);

    }

    @Test
    void delete() {
        Historico h = u.historico();
        h = service.save(h);
        Historico teste = service.findById(h.getId());
        Assertions.assertEquals(teste.getPesquisa(),h.getPesquisa());
        service.delete(teste);
        Assertions.assertNull(service.findById(h.getId()));
    }
}