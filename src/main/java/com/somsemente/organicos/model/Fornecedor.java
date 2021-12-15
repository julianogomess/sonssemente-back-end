package com.somsemente.organicos.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Document(collection =  "Fornecedores")
public class Fornecedor {
    @Id
    private String id;
    @NotBlank(message = "Campo Nome Completo é obrigatório")
    private String nome;
    @NotBlank (message = "Campo Email é obrigatório")
    @Email
    @Indexed(unique = true)
    private String email;
    @NotBlank (message = "Campo Celular é obrigatório")
    @Indexed(unique = true)
    private String telefone;
    @CNPJ
    @Indexed(unique = true)
    private String cnpj;
    @Valid
    @NotNull
    private Endereco endereco;
}
