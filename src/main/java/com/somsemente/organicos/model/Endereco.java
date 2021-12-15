package com.somsemente.organicos.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class Endereco {
    @NotBlank
    private String rua;
    @NotBlank
    private String numero;
    private String complemento;
    @NotBlank
    private String cep;
    @NotBlank
    private String bairro;
    @NotBlank
    private String cidade;
    @NotBlank
    private String uf;

}
