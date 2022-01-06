package com.somsemente.organicos.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class Endereco {
    @NotBlank(message = "Campo Rua do endereço é obrigatório")
    private String rua;
    @NotBlank(message = "Campo Numero do endereço é obrigatório")
    private String numero;
    private String complemento;
    @NotBlank(message = "Campo CEP do endereço é obrigatório")
    private String cep;
    @NotBlank(message = "Campo Bairro do endereço é obrigatório")
    private String bairro;
    @NotBlank(message = "Campo Cidade do endereço é obrigatório")
    private String cidade;
    @NotBlank(message = "Campo UF do endereço é obrigatório")
    private String uf;

}
