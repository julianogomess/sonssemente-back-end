package com.somsemente.organicos.service.impl;

import com.somsemente.organicos.model.Fornecedor;
import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.utils.Tipo;
import com.somsemente.organicos.repository.ProdutoRepository;
import com.somsemente.organicos.service.FornecedorService;
import com.somsemente.organicos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    @Autowired
    ProdutoRepository pr;

    @Autowired
    FornecedorService fornecedorService;

    @Override
    public List<Produto> findAll(){
        return pr.findAll();
    }

    @Override
    public Produto save(Produto produto, String cnpj){
        Fornecedor fornecedor = fornecedorService.findByCnpj(cnpj);
        if(fornecedor==null){
            return null;
        }
        produto.setFornecedor(fornecedor);
        return pr.save(produto);
    }

    @Override
    public List<Produto> findByTipo(Tipo tipo) {
        return pr.findByTipo(tipo);
    }

    @Override
    public void deleteById(String id) {
        pr.deleteById(id);
    }
}
