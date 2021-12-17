package com.somsemente.organicos.controller;

import com.somsemente.organicos.config.jwtConfig.JwtTokenProvider;
import com.somsemente.organicos.model.AuthBody;
import com.somsemente.organicos.model.User;
import com.somsemente.organicos.service.impl.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    CustomUserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody info){
        try{
            String username = info.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,info.getPassword()));
            String token = jwtTokenProvider.createToken(username,userService.findByEmail(username).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("email", username);
            model.put("token", token);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(model);
        }catch (AuthenticationException e){
            if (userService.findByEmail(info.getEmail()) == null) {
                throw new BadCredentialsException("Email Invalido!!");
            }
            throw new BadCredentialsException("Senha Invalida");
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity cadastro(@Valid @RequestBody User user){
        User u = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(u);
    }


}
