package com.somsemente.organicos.controller;


import com.somsemente.organicos.model.Fornecedor;
import com.somsemente.organicos.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {
    @Autowired
    FornecedorService fornecedorService;

    @GetMapping(value = "/listatodos")
    public ResponseEntity<Object> getTodosF(){
        List<Fornecedor> fornecedores = fornecedorService.findAll();
        if(fornecedores.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(fornecedores);
    }

    @GetMapping(value = "/buscaporcnpj/{cnpj}")
    public ResponseEntity<Object> getByCnpj(@PathVariable String cnpj){
        Fornecedor fornecedor = fornecedorService.findByCnpj(cnpj);
        if (fornecedor==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(fornecedor);
    }

    @PostMapping(value = "/cadastro")
    public ResponseEntity<Object> cadastroFornecedor(@Valid @RequestBody Fornecedor fornecedor){
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorService.save(fornecedor));
    }

    @DeleteMapping(value = "/deleteporcnpj/{cnpj}")
    public ResponseEntity<Object> deleteByCnpj(@PathVariable String cnpj){
        fornecedorService.deleteByCnpj(cnpj);
        return ResponseEntity.status(HttpStatus.OK).body("Fornecedor deletado!");
    }
}
