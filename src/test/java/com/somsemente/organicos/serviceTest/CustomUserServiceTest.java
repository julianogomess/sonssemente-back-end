package com.somsemente.organicos.serviceTest;

import com.somsemente.organicos.Utils;
import com.somsemente.organicos.model.utils.Role;
import com.somsemente.organicos.model.User;
import com.somsemente.organicos.repository.UserRepository;
import com.somsemente.organicos.service.impl.CustomUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CustomUserServiceTest {

    @Autowired
    CustomUserService service;

    @Autowired
    UserRepository repository;

    Utils u = new Utils();

    @Test
    void findByEmail() {
        User uTest = u.user();
        Assertions.assertNull(service.findByEmail(uTest.getEmail()));
        User userDelete = service.save(uTest);
        Assertions.assertNotNull(service.findByEmail(uTest.getEmail()));
        repository.delete(userDelete);
    }

    @Test
    void findByCpf() {
        User uTest = u.user();
        Assertions.assertNull(service.findByCpf(uTest.getCpf()));
        User userDelete = service.save(uTest);
        Assertions.assertNotNull(service.findByCpf(uTest.getCpf()));
        repository.delete(userDelete);
    }

    @Test
    void deleteByCpf() {
        User uTest = u.user();
        User user = service.save(uTest);
        Assertions.assertNotNull(repository.findByEmail(uTest.getEmail()));
        service.deleteByCpf(uTest.getCpf());
        Assertions.assertNull(repository.findByCpf(uTest.getCpf()));
    }

    @Test
    void findAll() {
        User uTest = u.user();
        List<User> lista = service.findAll();
        boolean teste = false;
        for (User l : lista) {
            if(l.getCpf().equals(uTest.getCpf())){
                teste = true;
            }
        }
        Assertions.assertFalse(teste);
        User userDelete = service.save(uTest);
        lista = service.findAll();
        teste = false;
        for (User l : lista) {
            if(l.getCpf().equals(uTest.getCpf())){
                teste = true;
            }
        }
        Assertions.assertTrue(teste);
        repository.delete(userDelete);
    }

    @Test
    void save() {
        User uTest = u.user();
        Assertions.assertNull(repository.findByCpf(uTest.getCpf()));
        User userDelete = service.save(uTest);
        User tests = repository.findByCpf(uTest.getCpf());
        Assertions.assertNotNull(tests);
        Assertions.assertNotNull(tests.getId());
        Assertions.assertTrue(tests.getRoles().contains(Role.ADMIN));
        Assertions.assertEquals(tests.getNome(),uTest.getNome());
        repository.delete(userDelete);
    }

}