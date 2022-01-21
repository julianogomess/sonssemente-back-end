package com.somsemente.organicos.controller;

import com.somsemente.organicos.dto.HistoricoDTO;
import com.somsemente.organicos.model.Historico;
import com.somsemente.organicos.model.Produto;

import com.somsemente.organicos.service.HistoricoService;
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
@RequestMapping("/api/pesquisas")
@Api(value = "Historico de pesquisas de Usuário")
@Slf4j
public class HistoricoController {

    @Autowired
    HistoricoService historicoService;
    @Autowired
    ProdutoService produtoService;


    @ApiOperation(value = "Retorna todos as pesquisas feitas")
    @GetMapping(value = "/listatodos")
    public ResponseEntity getAll(){
        Map<Object, Object> model = new HashMap<>();
        log.info("Busca por todos as pesquisas feitas");
        List<Historico> lista =  historicoService.findAll();
        if(lista.isEmpty()){
            log.info("Nenhuma pesquisa encontrada");
            model.put("message","Nenhuma pesquisa encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        log.info("Foram encontradas " + lista.size() + " pesquisas!");
        model.put("object",lista);
        model.put("message","Foram encontradas " + lista.size() + " pesquisas!");
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @ApiOperation(value = "Armazena uma nova pesquisa")
    @PostMapping(value = "/salvar")
    public ResponseEntity salvarPesquisa(@Valid @RequestBody HistoricoDTO dto){
        Map<Object, Object> model = new HashMap<>();
        String msg = "";
        log.info("Salvando nova pesquisa");
        List<Produto> produto = produtoService.pesquisaPorNome(dto.getPesquisa());
        Historico historico = new Historico();
        if (produto.isEmpty()){
            log.info("Produto não encontrado, apenas salvo o item pesquisado");
            historico = dto.transformar(null,dto.getPesquisa());
            msg+= " Produto não encontrado, apenas o nome da pesquisa. ";
        }else historico=dto.transformar(produto.get(0).getTipo(), produto.get(0).getNome());
        historico = historicoService.save(historico);
        log.info("Pesquisa salva com sucesso");
        msg+= "  Pesquisa salva com sucesso!";
        model.put("message",msg);
        model.put("object",historico);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @ApiOperation(value = "Deletar por id cada pesquisa")
    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity deleteById(@PathVariable String id){
        Map<Object, Object> model = new HashMap<>();
        log.info("Deletando pesquisa por id");
        Historico h = historicoService.findById(id);
        if(h==null){
            log.info("Historico não encontrado, id incorreto");
            model.put("message","Historico não encontrado, id incorreto");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        historicoService.delete(h);
        log.info("Historico deletado com sucesso");
        model.put("message","Historico deletado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

}
