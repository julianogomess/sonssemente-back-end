package com.somsemente.organicos.serviceTest;

import com.somsemente.organicos.Utils;
import com.somsemente.organicos.model.Fornecedor;
import com.somsemente.organicos.repository.FornecedorRepository;
import com.somsemente.organicos.service.FornecedorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class FornecedorServiceImplTest {

    @Autowired
    FornecedorService service;

    @Autowired
    FornecedorRepository repository;

    Utils u = new Utils();

    @Test
    void save() {
        Fornecedor ftest = u.fornecedor();
        Assertions.assertNull(repository.findByCnpj(ftest.getCnpj()));
        Fornecedor fbusca = service.save(ftest);
        Assertions.assertNotNull(repository.findByCnpj(ftest.getCnpj()));
        Assertions.assertEquals(fbusca.getNome(),ftest.getNome());
        Assertions.assertEquals(fbusca.getEmail(),ftest.getEmail());
        Assertions.assertEquals(fbusca.getEndereco().getCep(),ftest.getEndereco().getCep());
        repository.delete(fbusca);
    }

    @Test
    void findAll() {
        Fornecedor ftest = u.fornecedor();
        int contTest = repository.findAll().size();
        Assertions.assertEquals(contTest,service.findAll().size());
        Fornecedor f = repository.save(ftest);
        Assertions.assertEquals(contTest+1,service.findAll().size());
        List<Fornecedor> lista = service.findAll();
        boolean teste = false;
        for (Fornecedor l : lista) {
            if(l.getCnpj().equals(f.getCnpj())){
                teste = true;
            }
        }
        Assertions.assertTrue(teste);
        repository.delete(ftest);
    }

    @Test
    void findByCnpj() {
        Fornecedor ftest = u.fornecedor();
        ftest = repository.save(ftest);
        Assertions.assertNotNull(service.findByCnpj(ftest.getCnpj()));
        repository.delete(ftest);
    }

    @Test
    void deleteByCnpj() {
        Fornecedor ftest = u.fornecedor();
        Assertions.assertNull(repository.findByCnpj(ftest.getCnpj()));
        repository.save(ftest);
        Assertions.assertNotNull(repository.findByCnpj(ftest.getCnpj()));
        service.deleteByCnpj(ftest.getCnpj());
        Assertions.assertNull(repository.findByCnpj(ftest.getCnpj()));
    }

}
