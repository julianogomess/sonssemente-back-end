package com.somsemente.organicos.serviceTest;

import com.somsemente.organicos.Utils;
import com.somsemente.organicos.model.Fornecedor;
import com.somsemente.organicos.model.Pagamento;
import com.somsemente.organicos.repository.PagamentoRepository;
import com.somsemente.organicos.service.PagamentoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Date;
import java.util.List;


@SpringBootTest
public class PagamentoServiceImplTest {

    @Autowired
    PagamentoRepository repository;
    @Autowired
    PagamentoService service;
    Utils u = new Utils();


    @Test
    void save() {
        Pagamento pTeste = u.pagamento();
        Pagamento pRes = service.save(pTeste);
        Assertions.assertNotNull(pRes);
        Assertions.assertNotNull(repository.findById(pRes.getId()));
        Assertions.assertEquals(pTeste.getValor(),pRes.getValor());
        Assertions.assertEquals(pTeste.getVeiculo(),pRes.getVeiculo());
        repository.delete(pRes);
    }

    @Test
    void deleteById() {
        Pagamento pTeste = u.pagamento();
        Pagamento p = service.save(pTeste);
        Assertions.assertNotNull(service.findById(p.getId()));
        service.deleteById(p.getId());
        Assertions.assertNull(service.findById(p.getId()));
    }

    @Test
    void findAll() {
        int totalAntes = service.findAll().size();
        Pagamento pTeste = u.pagamento();
        pTeste = service.save(pTeste);
        List<Pagamento> lista = service.findAll();
        int totalDepois = lista.size();
        Assertions.assertEquals(totalAntes+1,totalDepois);
        boolean teste = false;
        for (Pagamento p : lista) {
            if(p.getId().equals(pTeste.getId())){
                teste = true;
            }
        }
        Assertions.assertTrue(teste);
        repository.delete(pTeste);
    }

    @Test
    void findById() {
        Pagamento preal = u.pagamento();
        preal = service.save(preal);
        Pagamento pteste = service.findById(preal.getId());
        Assertions.assertEquals(pteste.getVeiculo(),preal.getVeiculo());
        Assertions.assertEquals(pteste.getValor(),preal.getValor());
        repository.delete(pteste);
    }

    @Test
    void buscaAbertos() {
        int totalAntes = service.buscaAbertos().size();
        Pagamento pTeste = u.pagamento();
        pTeste = service.save(pTeste);
        List<Pagamento> lista = service.buscaAbertos();
        int totalDepois = lista.size();
        Assertions.assertEquals(totalAntes+1,totalDepois);
        boolean teste = false;
        for (Pagamento p : lista) {
            if(p.getId().equals(pTeste.getId())){
                teste = true;
            }
        }
        Assertions.assertTrue(teste);
        repository.delete(pTeste);
    }

    @Test
    void somaAbertos() {
        double somaAntes = service.somaAbertos();
        Pagamento pTeste = u.pagamento();
        pTeste = service.save(pTeste);
        double somaDepois = service.somaAbertos();
        Assertions.assertEquals(somaAntes+550,somaDepois);
        repository.delete(pTeste);
    }
}
