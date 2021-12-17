package com.somsemente.organicos.controller;


import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.Tipo;
import com.somsemente.organicos.service.ProdutoService;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping(value = "/listatodos")
    public ResponseEntity<Object> getProdutos(){
        return ResponseEntity.ok(produtoService.findAll());
    }

    @GetMapping(value = "/buscaportipo/{tipo}")
    public ResponseEntity<Object> getProdutoPorTipo(@PathVariable Tipo tipo){
        return ResponseEntity.status(HttpStatus.FOUND).body(produtoService.findByTipo(tipo));
    }
    @PostMapping(value = "/cadastro/{cnpj}")
    public ResponseEntity<Object> cadastroProduto(@RequestBody @Valid Produto produto, @PathVariable String cnpj){
        Produto p = produtoService.save(produto, cnpj);
        if(p==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CNPJ Invalido! Fornecedor não encontrado");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }
    @DeleteMapping(value = "/deletebyid/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable String id){
        produtoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado");
    }


}
