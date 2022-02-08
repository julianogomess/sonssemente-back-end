package com.somsemente.organicos.service.impl;

import com.somsemente.organicos.model.Fornecedor;
import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.utils.Tipo;
import com.somsemente.organicos.repository.ProdutoRepository;
import com.somsemente.organicos.service.FornecedorService;
import com.somsemente.organicos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public void deleteByNome(String nome) {
        pr.deleteByNome(nome);
    }

    @Override
    public Produto findByNome(String nome) {
        return pr.findByNome(nome);
    }

    @Override
    public Produto findById(String id) {
        Optional<Produto> p= pr.findById(id);
        if (p==null){
            return null;
        }
        return p.get();
    }

    @Override
    public List<Produto> pesquisaPorNome(String nome) {
        return pr.pesquisPorNome(nome);
    }

    @Override
    public List<Produto> getHome() {
        return pr.getHome();
    }

    @Override
    public Produto atualizarEstoque(Produto p, Double valor) {
        p.setEstoque(valor);
        return pr.save(p);
    }

    @Override
    public Map<Object, Object> listaPaginada() {
        List<Produto> listaVerdura = pickFive(findByTipo(Tipo.Verdura));
        List<Produto> listaFruta = pickFive(findByTipo(Tipo.Fruta));
        List<Produto> listaLegume = pickFive(findByTipo(Tipo.Legume));
        Map<Object, Object> map = new HashMap<>();
        map.put("Verdura",listaVerdura);
        map.put("Fruta",listaFruta);
        map.put("Legume",listaLegume);
        return map;
    }

    private List<Produto> pickFive(List<Produto> lista){
        List<Produto> result = new ArrayList<>();
        Collections.shuffle(lista);
        int max = 5;
        if (lista.size()<5){
            max=lista.size();
        }
        for (int i=0;i<max;i++){
            result.add(lista.get(i));
        }
        return result;
    }
}
