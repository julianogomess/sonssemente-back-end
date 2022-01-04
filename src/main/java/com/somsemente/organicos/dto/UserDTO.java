package com.somsemente.organicos.dto;

import com.somsemente.organicos.model.Endereco;
import com.somsemente.organicos.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    @NotBlank(message = "Campo Nome Completo é obrigatório")
    private String nome;
    @NotBlank (message = "Campo Data de Nascimento é obrigatório")
    private String dataNasc;
    @NotBlank (message = "Campo Email é obrigatório")
    @Email(message = "Email precisa estar validado")
    @Indexed(unique = true)
    private String email;
    @NotEmpty(message = "Campo CPF é obrigatório")
    @CPF(message = "CPF precisa ser valido!")
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

    public User trasnformar(){
        User u = new User();
        u.setEndereco(this.getEndereco());
        u.setPassword(this.getPassword());
        u.setTelefone(this.getTelefone());
        u.setEmail(this.getEmail());
        u.setCpf(this.getCpf());
        u.setNome(this.getNome());
        u.setDataNasc(this.getDataNasc());
        return u;
    }
}
