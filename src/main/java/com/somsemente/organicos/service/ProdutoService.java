package com.somsemente.organicos.service;

import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.Tipo;

import java.util.List;

public interface ProdutoService {
    List<Produto> findAll();
    Produto save(Produto produto,String cnpj);
    List<Produto> findByTipo(Tipo tipo);
    void deleteById(String id);
}
