package com.somsemente.organicos.controller;


import com.somsemente.organicos.dto.ProdutoDTO;
import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.utils.Tipo;
import com.somsemente.organicos.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
@Api(value = "Gerenciamento de produtos")
@Slf4j
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;


    @ApiOperation(value = "Retorna todos os produtos")
    @GetMapping(value = "/listatodos")
    public ResponseEntity<Object> getProdutos(){
        Map<Object, Object> model = new HashMap<>();
        log.info("Busca por todos os produtos");
        List<Produto> produtos =  produtoService.findAll();
        if(produtos.isEmpty()){
            log.info("Nenhum produto encontrado");
            model.put("message","Nenhum produto encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        log.info("Foram encontrados " + produtos.size() + " produtos!");
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @ApiOperation(value = "Retorna todos os produtos por tipo")
    @GetMapping(value = "/buscaportipo/{tipo}")
    public ResponseEntity<Object> getProdutoPorTipo(@PathVariable Tipo tipo){
        Map<Object, Object> model = new HashMap<>();
        log.info("busca por tipo de produto");
        List<Produto> produtos =  produtoService.findByTipo(tipo);
        if(produtos.isEmpty()){
            log.info("Nenhum produto encontrado!");
            model.put("message","Nenhum produto encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);}
        log.info("Foram encontrados " + produtos.size() + " produtos do tipo: "+ tipo);
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @ApiOperation(value = "Cadastro do produto relacionado ao fornecedor do mesmo")
    @PostMapping(value = "/cadastro/{cnpj}")
    public ResponseEntity<Object> cadastroProduto(@RequestBody @Valid ProdutoDTO produto, @PathVariable String cnpj){
        Map<Object, Object> model = new HashMap<>();
        log.info("Cadastro de novo produto relacionado ao fornecedor");
        Produto p = produtoService.save(produto.trasnformar(), cnpj);
        if(p==null){
            log.info("CNPJ informado invalido ou fornecedor não cadastrado!");
            model.put("message","CNPJ Invalido! Fornecedor não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        log.info("Produto criado!");
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @ApiOperation(value = "Exclui o produto por id")
    @DeleteMapping(value = "/deleteporid/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable String id){
        Map<Object, Object> model = new HashMap<>();
        Produto p = produtoService.findById(id);
        if (p==null){
            log.info("Produto não encontrado!");
            model.put("message","Produto não encontrado");
            return ResponseEntity.status(HttpStatus.OK).body(model);
        }
        produtoService.deleteById(id);
        log.info("Produto deletado!");
        model.put("message","Produto deletado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }


}
