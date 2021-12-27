package com.somsemente.organicos.model;


import com.somsemente.organicos.model.utils.Tipo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Produtos")
@Getter
@Setter
@NoArgsConstructor
public class Produto {
    @Id
    private String id;
    private String nome;
    private Tipo tipo;
    private Double preco;
    @DBRef
    private Fornecedor fornecedor;

}
