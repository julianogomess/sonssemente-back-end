package com.somsemente.organicos.controller;


import com.somsemente.organicos.model.Fornecedor;
import com.somsemente.organicos.service.FornecedorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
@Api(value = "Gerenciamento de clientes")
@Slf4j
public class FornecedorController {
    @Autowired
    FornecedorService fornecedorService;

    @ApiOperation(value = "Retorna todos os fornecedores na base")
    @GetMapping(value = "/listatodos")
    public ResponseEntity<Object> getTodosF(){
        log.info("Busca por todos os fornecedores");
        List<Fornecedor> fornecedores = fornecedorService.findAll();
        if(fornecedores.isEmpty()){
            log.info("Nenhum fornecedor encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        log.info(fornecedores.size()+ " fornecedores encontrados!");
        return ResponseEntity.status(HttpStatus.FOUND).body(fornecedores);
    }

    @ApiOperation(value = "Retorna os dados do fornecedor passando o cnpj")
    @GetMapping(value = "/buscaporcnpj/{cnpj}")
    public ResponseEntity<Object> getByCnpj(@PathVariable String cnpj){
        log.info("Busca de fornecedor por CNPJ");
        Fornecedor fornecedor = fornecedorService.findByCnpj(cnpj);
        if (fornecedor==null){
            log.info("Fornecedor não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        log.info("Fornecedor encontrado: " + fornecedor.getNome());
        return ResponseEntity.status(HttpStatus.FOUND).body(fornecedor);
    }
    @ApiOperation(value = "Realiza o cadastro do fornecedor")
    @PostMapping(value = "/cadastro")
    public ResponseEntity<Object> cadastroFornecedor(@Valid @RequestBody Fornecedor fornecedor){
        log.info("Cadastro de novo fornecedor");
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorService.save(fornecedor));
    }

    @ApiOperation(value = "Exclui o fornecedor por cnpj")
    @DeleteMapping(value = "/deleteporcnpj/{cnpj}")
    public ResponseEntity<Object> deleteByCnpj(@PathVariable String cnpj){
        log.info("Fornecedor deletado! CNPJ: "+ cnpj);
        fornecedorService.deleteByCnpj(cnpj);
        return ResponseEntity.status(HttpStatus.OK).body("Fornecedor deletado!");
    }
}
