package com.somsemente.organicos.serviceTest;

import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.Tipo;
import com.somsemente.organicos.repository.FornecedorRepository;
import com.somsemente.organicos.repository.ProdutoRepository;
import com.somsemente.organicos.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProdutoServiceImplTest {

    @Autowired
    ProdutoService service;

    @Autowired
    ProdutoRepository repository;

    @Autowired
    FornecedorRepository fornecedorRepository;

    Utils u = new Utils();

    @Test
    void findAll() {
        Produto pTest = u.produto();
        pTest.setFornecedor(fornecedorRepository.save(pTest.getFornecedor()));
        int count = repository.findAll().size();
        Assertions.assertEquals(count, service.findAll().size());
        pTest = repository.save(pTest);
        List<Produto> lista = service.findAll();
        Assertions.assertEquals(count +1, lista.size());
        boolean teste = false;
        for (Produto p : lista) {
            if(p.getId().equals(pTest.getId())){
                teste = true;
            }
        }
        Assertions.assertTrue(teste);
        repository.delete(pTest);
        fornecedorRepository.delete(pTest.getFornecedor());
    }

    @Test
    void save() {
        Produto ptest = u.produto();
        ptest.setFornecedor(fornecedorRepository.save(ptest.getFornecedor()));
        Assertions.assertNull(repository.findByNome(ptest.getNome()));
        ptest = service.save(ptest,ptest.getFornecedor().getCnpj());
        Assertions.assertNotNull(repository.findByNome(ptest.getNome()));
        repository.delete(ptest);
        fornecedorRepository.delete(ptest.getFornecedor());
    }

    @Test
    void findByTipo() {
        Produto ptest = u.produto();
        ptest.setFornecedor(fornecedorRepository.save(ptest.getFornecedor()));
        int count = repository.findByTipo(Tipo.Verdura).size();
        repository.save(ptest);
        List<Produto> lista = service.findByTipo(Tipo.Verdura);
        Assertions.assertEquals(count +1, lista.size());
        boolean teste = false;
        for (Produto p : lista) {
            if(p.getId().equals(ptest.getId())){
                teste = true;
            }
        }
        Assertions.assertTrue(teste);
        repository.delete(ptest);
        fornecedorRepository.delete(ptest.getFornecedor());
    }

    @Test
    void deleteById() {
        Produto ptest = u.produto();
        ptest.setFornecedor(fornecedorRepository.save(ptest.getFornecedor()));
        ptest = repository.save(ptest);
        Assertions.assertNotNull(repository.findById(ptest.getId()));
        service.deleteById(ptest.getId());
        Produto p = repository.findByNome(ptest.getNome());
        Assertions.assertNull(p);
        fornecedorRepository.delete(ptest.getFornecedor());

    }


}