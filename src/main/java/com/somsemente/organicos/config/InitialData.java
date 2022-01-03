package com.somsemente.organicos.config;

import com.somsemente.organicos.model.Endereco;
import com.somsemente.organicos.model.Fornecedor;
import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.User;
import com.somsemente.organicos.model.utils.Role;
import com.somsemente.organicos.model.utils.Tipo;
import com.somsemente.organicos.service.FornecedorService;
import com.somsemente.organicos.service.ProdutoService;
import com.somsemente.organicos.service.impl.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class InitialData implements ApplicationListener<ContextRefreshedEvent> {
    private boolean setup= false;

    @Autowired
    CustomUserService userService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    FornecedorService fornecedorService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (setup) {
            return;
        }

        createAdminUser();
        createFornecedor();
        createProd();

        setup = true;

    }

    private void createAdminUser(){
        User user = userService.findByEmail("abc@ibm.com");
        if (user==null){
            user = new User();
            user.setNome("Juliano Gomes Sousa");
            user.setEndereco(this.endereco());
            user.setPassword("abc123");
            user.setTelefone("11975741992");
            user.setDataNasc("23/09/1999");
            user.setCpf("49507369813");
            user.setEmail("abc@ibm.com");
            Set<Role> roles = new HashSet<>();
            roles.add(Role.ADMIN);
            user.setRoles(roles);
            userService.save(user);
        }
    }

    private void createFornecedor(){
        Fornecedor f = fornecedorService.findByCnpj("07199027000102");
        if(f==null){
            f = new Fornecedor();
            f.setNome("Fazenda Chico Bento");
            f.setCnpj("07199027000102");
            f.setTelefone("11975741992");
            f.setEndereco(this.endereco());
            f.setEmail("abc@ibm.com");
            fornecedorService.save(f);
        }
    }

    private void createProd(){
        String cnpj = "07199027000102";
        Produto p1 = produtoService.findByNome("Alface Blue");
        if (p1==null){
            p1 = new Produto();
            p1.setPreco(1.0);
            p1.setTipo(Tipo.Verdura);
            p1.setNome("Alface Blue");
            produtoService.save(p1,cnpj);
        }
        Produto p2 = produtoService.findByNome("Repolho Blue");
        if (p2==null){
            p2 = new Produto();
            p2.setPreco(1.0);
            p2.setTipo(Tipo.Verdura);
            p2.setNome("Repolho Blue");
            produtoService.save(p2,cnpj);
        }
        Produto p3 = produtoService.findByNome("Repolho Red");
        if (p3==null){
            p3 = new Produto();
            p3.setPreco(1.0);
            p3.setTipo(Tipo.Verdura);
            p3.setNome("Repolho Red");
            produtoService.save(p3,cnpj);
        }

    }


    public Endereco endereco(){
        Endereco e = new Endereco();
        e.setCep("0760123");
        e.setBairro("Santa Ines");
        e.setCidade("Mairiporã");
        e.setNumero("10");
        e.setRua("Rua Paulo Soares");
        e.setUf("SP");
        return e;
    }
}
