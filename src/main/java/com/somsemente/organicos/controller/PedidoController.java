package com.somsemente.organicos.controller;


import com.somsemente.organicos.dto.ItemPedidoDTO;
import com.somsemente.organicos.model.ItemPedido;
import com.somsemente.organicos.model.Pedido;
import com.somsemente.organicos.model.User;
import com.somsemente.organicos.model.utils.StatusPedido;
import com.somsemente.organicos.service.PedidoService;
import com.somsemente.organicos.service.ProdutoService;
import com.somsemente.organicos.service.impl.CustomUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@Api(value = "Gerenciamento de Pedidos")
@Slf4j
public class PedidoController {
    @Autowired
    PedidoService pedidoService;
    @Autowired
    CustomUserService userService;


    @ApiOperation(value = "Retorna todos os produtos")
    @GetMapping(value = "/listatodos")
    public ResponseEntity getPedidos(){
        Map<Object, Object> model = new HashMap<>();
        log.info("Busca por todos os pedidos");
        List<Pedido> pedidos =  pedidoService.findAll();
        if(pedidos.isEmpty()){
            log.info("Nenhum pedido encontrado");
            model.put("message","Nenhum pedido encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum pedido encontrado");
        }
        log.info("Foram encontrados " + pedidos.size() + " pedidos!");
        return ResponseEntity.status(HttpStatus.OK).body(pedidos);
    }

    @ApiOperation(value = "Retorna todos os pedidos do Usuário")
    @GetMapping(value = "/buscaporuser/{cpf}")
    public ResponseEntity<Object> getPedidoPorUsuario(@PathVariable String cpf){
        Map<Object, Object> model = new HashMap<>();
        log.info("busca por usuário");
        List<Pedido> pedidos = pedidoService.findByUser(cpf);
        if(pedidos.isEmpty()){
            log.info("Nenhum pedido encontrado!");
            model.put("message","Nenhum pedido encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);}
        log.info("Foram encontrados " + pedidos.size() + " pedidos feitos pelo usuário de CPF: "+ cpf);
        return ResponseEntity.status(HttpStatus.OK).body(pedidos);
    }

    @ApiOperation(value = "Retorna os pedidos não finalizados")
    @GetMapping(value = "/buscaabertos")
    public ResponseEntity getPedidosAbertos(){
        Map<Object, Object> model = new HashMap<>();
        log.info("Busca todos os pedidos que ainda não foram finalizados");
        List<Pedido> pedidos = pedidoService.findNaoFinalizado();
        if(pedidos.isEmpty()){
            log.info("Nenhum pedido encontrado!");
            model.put("message","Nenhum pedido encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);}
        log.info("Foram encontrados " + pedidos.size());
        return ResponseEntity.status(HttpStatus.OK).body(pedidos);
    }

    @ApiOperation(value = "Cadastra um novo pedido na base de dados")
    @PostMapping(value = "/cadastro/{email}")
    public ResponseEntity cadastroPedido(@RequestBody List<ItemPedidoDTO> lista, @PathVariable String email) {
        Map<Object, Object> model = new HashMap<>();
        List<ItemPedido> items  = pedidoService.transformarDTO(lista);
        log.info("Cadastro de pedido iniciado, busca do usuário por email");
        User user = userService.findByEmail(email);
        if (user == null) {
            log.info("Email invalido, usuário não encontrado!");
            model.put("message", "Email invalido, usuário não encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        if (!checkPedido(items)){
            log.info("Houve um problema com estoque do produto");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnMsg(items));
        }
        Pedido pedido = pedidoService.save(items, user);
        log.info("Pedido cadastrado com sucesso");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        }finally {
            pedidoService.atualizarEstoque(items);
            pedidoService.mailCadastro(pedido);
        }
    }

    @ApiOperation(value = "Deletar pedido por id")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deletePedido(@PathVariable String id){
        Map<Object, Object> model = new HashMap<>();
        log.info("Busca por id do pedido");
        Pedido pedido = pedidoService.findById(id);
        if (pedido==null){
            log.info("Pedido nao encontrado");
            model.put("message","Pedido não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        pedidoService.delete(pedido);
        log.info("Pedido deletado com sucesso");
        model.put("message","Pedido deletado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }


    @ApiOperation(value = "Atualiza o status do pedido")
    @PutMapping(value = "/status/{id}/{status}")
    public ResponseEntity separarPedido(@PathVariable("id") String id, @PathVariable("status") StatusPedido statusPedido){
        Map<Object, Object> model = new HashMap<>();
        log.info("Busca por id do pedido");
        Pedido pedido = pedidoService.findById(id);
        if (pedido==null){
            log.info("Pedido nao encontrado");
            model.put("message","Pedido não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        pedido = pedidoService.updateStatus(pedido, statusPedido);
        log.info("Pedido atualizado");
        model.put("message","Pedido atualizado com sucesso");
        model.put("object",pedido);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @ApiOperation(value = "Atualiza os itens de um pedido")
    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity atualizarPedido(@RequestBody List<ItemPedido> items, @PathVariable String id){
        Map<Object, Object> model = new HashMap<>();
        log.info("Cadastro de pedido iniciado, busca do pedido por ID");
        Pedido pedido = pedidoService.findById(id);
        if(pedido==null){
            log.info("Id invalido, pedido não encontrado!");
            model.put("message","Id invalido, pedido não encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
        }
        pedido.setLista(items);
        pedido = pedidoService.updatePedido(pedido);
        log.info("Pedido atualizado com com sucesso");
        model.put("message","Pedido atualizado com sucesso");
        model.put("object",pedido);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(model);
        }finally {
            pedidoService.mailStatus(pedido);
            log.info("email enviado com status do pedido");
        }
    }

    private boolean checkPedido(List<ItemPedido> lista){
        for(ItemPedido i: lista){
            if (i.getQuantidade()>i.getProduto().getEstoque()){
                return false;
            }
        }
        return true;
    }
    private Map returnMsg(List<ItemPedido> lista){
        Map<Object, Object> model = new HashMap<>();
        String msg = "Há produtos na seu pedido onde o estoque acabou! Foram estes produtos: " ;
        int count = 0;
        for(ItemPedido i: lista){
            if (i.getQuantidade()>i.getProduto().getEstoque()){
                if (count == 0){
                    msg+= i.getProduto().getNome();
                    count+=1;
                }else {
                    msg += ", " + i.getProduto().getNome();
                }
            }
        }
        msg+= " .";
        model.put("message",msg);
        return model;
    }
}
