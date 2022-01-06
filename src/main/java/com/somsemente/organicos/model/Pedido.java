package com.somsemente.organicos.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Document(collection = "Pedidos")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {
    @Id
    private String id;
    @DBRef
    private User cliente;
    private List<ItemPedido> lista;
    private Date data;
    private boolean finalizado;
    private Double valor;
}
