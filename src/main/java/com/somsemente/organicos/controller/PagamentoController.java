package com.somsemente.organicos.controller;

import com.somsemente.organicos.dto.PagamentoDTO;
import com.somsemente.organicos.model.Pagamento;
import com.somsemente.organicos.service.PagamentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pagamentos")
@Api(value = "Gerenciamento de Pagamentos")
@Slf4j
public class PagamentoController {

    @Autowired
    PagamentoService pagamentoService;

    @ApiOperation(value = "Retorna todos os pagamentos")
    @GetMapping(value = "/listatodos")
    public ResponseEntity<Object> getPagamentos(){
        Map<Object, Object> model = new HashMap<>();
        log.info("Busca por todos os pagamentos");
        List<Pagamento> lista =  pagamentoService.findAll();
        if(lista.isEmpty()){
            log.info("Nenhum pagamento encontrado");
            model.put("message","Nenhum pagamento encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        log.info("Foram encontrados " + lista.size() + " notas de pagamento!");
        model.put("object",lista);
        model.put("message","Foram encontrados " + lista.size() + " notas de pagamento!");
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @ApiOperation(value = "Retorna os dados de um pagamento por id")
    @GetMapping(value = "/buscaporid/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id){
        Map<Object, Object> model = new HashMap<>();
        log.info("Busca de pagamento por id");
        Pagamento pagamento = pagamentoService.findById(id);
        if (pagamento==null){
            log.info("Pagamento não encontrado");
            model.put("message","Nenhum pagamento encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        log.info("Pagamento encontrado");
        model.put("message","Pagamento encontrado");
        model.put("object",pagamento);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @ApiOperation(value = "Realiza o cadastro do pagamento")
    @PostMapping(value = "/cadastro")
    public ResponseEntity<Object> cadastroPagamento(@Valid @RequestBody PagamentoDTO pagamentoDTO) throws ParseException {
        log.info("Cadastro de novo pagamento");
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.save(pagamentoDTO.transformar()));
    }

    @ApiOperation(value = "Deleta um pagamento")
    @DeleteMapping(value = "/deleteporid/{id}")
    public ResponseEntity<Object> deletePagamento(@PathVariable String id){
        Map<Object, Object> model = new HashMap<>();
        log.info("Delete de pagamento");
        boolean teste = pagamentoService.deleteById(id);
        if (teste){
            model.put("message","Pagamento deletado com sucesso");
            log.info("Pagamento deletado com sucesso");
            return ResponseEntity.status(HttpStatus.OK).body(model);
        }
        model.put("message","Pagamento não encontrado!");
        log.info("Pagamento não encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
    }

    @ApiOperation(value = "Retorna os pagamentos em aberto")
    @GetMapping(value = "/buscaporabertos")
    public ResponseEntity<Object> getAbertos(){
        Map<Object, Object> model = new HashMap<>();
        log.info("Busca de pagamento em aberto");
        List<Pagamento> pagamento = pagamentoService.buscaAbertos();
        if (pagamento==null){
            log.info("Pagamento não encontrado");
            model.put("message","Nenhum pagamento encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        log.info("Pagamentos encontrados");
        model.put("message","Pagamentos encontrado");
        model.put("object",pagamento);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @ApiOperation(value = "Retorna a soma de todos pagamentos em aberto")
    @GetMapping(value = "/somaabertos")
    public ResponseEntity<Object> getsoma(){
        Map<Object, Object> model = new HashMap<>();
        log.info("Retorna a soma do valor em aberto");
        double soma = pagamentoService.somaAbertos();
        log.info("Valor em aberto: " + soma);
        model.put("message","Valor em aberto: " + soma);
        model.put("object",soma);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

}
