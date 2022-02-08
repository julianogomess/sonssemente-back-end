package com.somsemente.organicos.dto;

import com.somsemente.organicos.model.ItemPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemPedidoDTO {

    private String id;
    private Double quantidade;

}
