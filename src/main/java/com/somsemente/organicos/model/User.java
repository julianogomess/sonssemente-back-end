package com.somsemente.organicos.model;


import com.somsemente.organicos.model.utils.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Clientes")
public class User {
    @Id
    private String id;
    private String nome;
    private String dataNasc;
    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true)
    private String cpf;
    @Indexed(unique = true)
    private String telefone;
    private String password;
    @Valid
    private Endereco endereco;
    private Date dataCriacao;
    private boolean enabled;
    private Set<Role> roles;
}
