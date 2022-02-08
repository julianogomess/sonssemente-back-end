package com.somsemente.organicos.service;

import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.utils.Tipo;

import java.util.List;
import java.util.Map;

public interface ProdutoService {
    List<Produto> findAll();
    Produto save(Produto produto,String cnpj);
    List<Produto> findByTipo(Tipo tipo);
    void deleteById(String id);
    void deleteByNome(String nome);
    Produto findByNome(String nome);
    Produto findById(String id);
    List<Produto> pesquisaPorNome(String nome);
    List<Produto> getHome();
    Produto atualizarEstoque(Produto p, Double valor);
    Map<Object, Object> listaPaginada();
}
