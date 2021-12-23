package com.somsemente.organicos;

import com.somsemente.organicos.model.*;
import com.somsemente.organicos.model.utils.Tipo;

public class Utils {
    public Fornecedor fornecedor(){
        Fornecedor f = new Fornecedor();
        f.setEmail("123@ibm.com");
        f.setCnpj("86889939000174");
        f.setEndereco(this.endereco());
        f.setNome("Casa de Legumes");
        f.setTelefone("14999091234");
        return f;
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

    public Produto produto(){
        Produto p = new Produto();
        p.setFornecedor(this.fornecedor());
        p.setNome("Cebola Roxa");
        p.setPreco(2.0);
        p.setTipo(Tipo.Verdura);
        return p;
    }

    public User user(){
        User u = new User();
        u.setNome("Livia");
        u.setCpf("60777916010");
        u.setEmail("qwe@ibm.com");
        u.setDataNasc("23-12-1998");
        u.setPassword("123455");
        u.setTelefone("1199901221");
        u.setEndereco(this.endereco());
        return u;
    }
}
