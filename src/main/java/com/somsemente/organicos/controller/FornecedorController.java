package com.somsemente.organicos.controller;


import com.somsemente.organicos.dto.FornecedorDTO;
import com.somsemente.organicos.model.Fornecedor;
import com.somsemente.organicos.service.FornecedorService;
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
@RequestMapping("/api/fornecedores")
@Api(value = "Gerenciamento de clientes")
@Slf4j
public class FornecedorController {
    @Autowired
    FornecedorService fornecedorService;


    @ApiOperation(value = "Retorna todos os fornecedores na base")
    @GetMapping(value = "/listatodos")
    public ResponseEntity<Object> getTodosF(){
        Map<Object, Object> model = new HashMap<>();
        log.info("Busca por todos os fornecedores");
        List<Fornecedor> fornecedores = fornecedorService.findAll();
        if(fornecedores.isEmpty()){
            log.info("Nenhum fornecedor encontrado!");
            model.put("message","Nenhum fornecedor encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        log.info(fornecedores.size()+ " fornecedores encontrados!");
        return ResponseEntity.status(HttpStatus.OK).body(fornecedores);
    }

    @ApiOperation(value = "Retorna os dados do fornecedor passando o cnpj")
    @GetMapping(value = "/buscaporcnpj/{cnpj}")
    public ResponseEntity<Object> getByCnpj(@PathVariable String cnpj){
        Map<Object, Object> model = new HashMap<>();
        log.info("Busca de fornecedor por CNPJ");
        Fornecedor fornecedor = fornecedorService.findByCnpj(cnpj);
        if (fornecedor==null){
            log.info("Fornecedor não encontrado");
            model.put("message","Nenhum fornecedor encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        log.info("Fornecedor encontrado: " + fornecedor.getNome());
        return ResponseEntity.status(HttpStatus.OK).body(fornecedor);
    }
    @ApiOperation(value = "Realiza o cadastro do fornecedor")
    @PostMapping(value = "/cadastro")
    public ResponseEntity<Object> cadastroFornecedor(@Valid @RequestBody FornecedorDTO fornecedor){
        log.info("Cadastro de novo fornecedor");
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorService.save(fornecedor.transformar()));
    }

    @ApiOperation(value = "Exclui o fornecedor por cnpj")
    @DeleteMapping(value = "/deleteporcnpj/{cnpj}")
    public ResponseEntity<Object> deleteByCnpj(@PathVariable String cnpj){
        Map<Object, Object> model = new HashMap<>();
        Fornecedor f = fornecedorService.findByCnpj(cnpj);
        if (f==null){
            log.info("Fornecedor não encontrado");
            model.put("message","Nenhum fornecedor encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        log.info("Fornecedor deletado! CNPJ: "+ cnpj);
        fornecedorService.deleteByCnpj(cnpj);
        model.put("message","Fornecedor deletado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @ApiOperation(value = "Edita o fornecedor")
    @PutMapping(value = "/atualizar/{cnpj}")
    public ResponseEntity atualizarFornecedor(@PathVariable String cnpj, @RequestBody @Valid FornecedorDTO fornecedor){
        Map<Object, Object> model = new HashMap<>();
        log.info("Busca do fornecedor para atualizar");
        Fornecedor f = fornecedorService.findByCnpj(cnpj);
        if (f==null){
            log.info("Fornecedor não encontrado");
            model.put("message","Nenhum fornecedor encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        f = fornecedorService.atualizar(f,fornecedor);
        log.info("Fornecedor atualizado!");
        return ResponseEntity.status(HttpStatus.OK).body(f);
    }
}
