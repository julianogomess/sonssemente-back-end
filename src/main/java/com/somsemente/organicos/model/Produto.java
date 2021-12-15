package com.somsemente.organicos.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Document(collection = "Produtos")
@Getter
@Setter
@NoArgsConstructor
public class Produto {
    @Id
    private String id;
    @NotBlank
    private String nome;
    @NotNull
    private Tipo tipo;
    @PositiveOrZero
    private Double preco;
    @DBRef
    private Fornecedor fornecedor;

}
