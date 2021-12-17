package com.somsemente.organicos.serviceTest;

import com.somsemente.organicos.model.Endereco;
import com.somsemente.organicos.model.Fornecedor;
import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.Tipo;

public class Utils {
    protected Fornecedor fornecedor(){
        Fornecedor f = new Fornecedor();
        f.setEmail("123@ibm.com");
        f.setCnpj("86889939000174");
        f.setEndereco(this.endereco());
        f.setNome("Casa de Legumes");
        f.setTelefone("14999091234");
        return f;
    }

    protected Endereco endereco(){
        Endereco e = new Endereco();
        e.setCep("0760123");
        e.setBairro("Santa Ines");
        e.setCidade("Mairiporã");
        e.setNumero("10");
        e.setRua("Rua Paulo Soares");
        e.setUf("SP");
        return e;
    }

    protected Produto produto(){
        Produto p = new Produto();
        p.setFornecedor(this.fornecedor());
        p.setNome("Cebola Roxa");
        p.setPreco(2.0);
        p.setTipo(Tipo.Verdura);
        return p;
    }
}
