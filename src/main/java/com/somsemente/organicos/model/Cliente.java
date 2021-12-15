package com.somsemente.organicos.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class Cliente {
    @Id
    private String id;
    @NotBlank(message = "Campo Nome Completo é obrigatório")
    private String nome;
    @NotBlank (message = "Campo Data de Nascimento é obrigatório")
    private String dataNasc;
    @NotBlank (message = "Campo Email é obrigatório")
    @Email
    @Indexed(unique = true)
    private String email;
    @NotEmpty(message = "Campo CPF é obrigatório")
    @CPF
    @Indexed(unique = true)
    private String cpf;
    @NotBlank (message = "Campo Celular é obrigatório")
    @Indexed(unique = true)
    private String telefone;
    @NotBlank (message = "Campo Senha é obrigatório")
    private String password;
    @Valid
    @NotNull
    private Endereco endereco;
    private String dataCriacao;
}
