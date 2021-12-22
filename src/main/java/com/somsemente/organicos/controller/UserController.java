package com.somsemente.organicos.controller;

import com.somsemente.organicos.config.jwtConfig.JwtTokenProvider;
import com.somsemente.organicos.model.AuthBody;
import com.somsemente.organicos.model.User;
import com.somsemente.organicos.service.impl.CustomUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Api(value = "Gerenciamento de usuários e login")
@Slf4j
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    CustomUserService userService;

    @ApiOperation(value = "Login para cliente")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody info){
        try{
            log.info("Login de cliente");
            String username = info.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,info.getPassword()));
            log.info("Cliente autenticado!");
            String token = jwtTokenProvider.createToken(username,userService.findByEmail(username).getRoles());
            log.info("Toker gerado!");
            Map<Object, Object> model = new HashMap<>();
            model.put("email", username);
            model.put("token", token);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(model);
        }catch (AuthenticationException e){
            if (userService.findByEmail(info.getEmail()) == null) {
                log.error("Cliente não autenticado, email invalido");
                throw new BadCredentialsException("Email Invalido!!");
            }
            log.error("Cliente não autenticado, senha errada");
            throw new BadCredentialsException("Senha Invalida");
        }
    }

    @ApiOperation(value = "Cadastro do cliente")
    @PostMapping("/cadastro")
    public ResponseEntity cadastro(@Valid @RequestBody User user){
        User u = userService.save(user);
        log.info("Cliente cadastrado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(u);
    }

    @ApiOperation(value = "Retorna todos os clientes")
    @GetMapping("/listatodos")
    public ResponseEntity getAll(){
        log.info("Busca por todos os clientes");
        List<User> users = userService.findAll();
        if (users.isEmpty()){
            log.info("Nenhum cliente encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há clientes na base");
        }
        log.info(users.size()+" clientes encontrado!");
        return ResponseEntity.status(HttpStatus.FOUND).body(users);
    }

    @ApiOperation(value = "Retorna o cliente por cpf")
    @GetMapping("/buscaporcpf/{cpf}")
    public ResponseEntity getByCpf(@PathVariable("cpf") String cpf){
        log.info("Busca por CPF");
        User user = userService.findByCpf(cpf);
        if (user==null){
            log.info("Nenhum cliente encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum cliente encontrado com este cpf");
        }
        log.info("Cliente " + user.getNome() + " encontrado.");
        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }

    @ApiOperation(value = "Exclui o cliente por cpf")
    @DeleteMapping("/delete/{cpf}")
    public ResponseEntity deleteByCpf(@PathVariable String cpf){
        userService.deleteByCpf(cpf);
        log.info("Cliente deletado!");
        return ResponseEntity.status(HttpStatus.OK).body("Cliente Deletado!");
    }


}
