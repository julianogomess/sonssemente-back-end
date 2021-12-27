package com.somsemente.organicos.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Getter
@Setter
@NoArgsConstructor
public class ItemPedido {

    @DBRef
    private Produto produto;
    private Double quantidade;
}
