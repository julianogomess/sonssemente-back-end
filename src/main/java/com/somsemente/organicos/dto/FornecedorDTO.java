package com.somsemente.organicos.dto;

import com.somsemente.organicos.model.Endereco;
import com.somsemente.organicos.model.Fornecedor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class FornecedorDTO {
    @NotBlank(message = "Campo Nome Completo é obrigatório")
    private String nome;
    @NotBlank (message = "Campo Email é obrigatório")
    @Email(message = "Email deve estar bem formatado")
    @Indexed(unique = true)
    private String email;
    @NotBlank (message = "Campo Celular é obrigatório")
    @Indexed(unique = true)
    private String telefone;
    @CNPJ(message = "CNPJ deve estar valido")
    @Indexed(unique = true)
    private String cnpj;
    @Valid
    @NotNull
    private Endereco endereco;

    public Fornecedor transformar(){
        Fornecedor f = new Fornecedor();
        f.setNome(this.getNome());
        f.setEndereco(this.getEndereco());
        f.setTelefone(this.getTelefone());
        f.setCnpj(this.getCnpj());
        f.setEmail(this.getEmail());
        return f;
    }
}
