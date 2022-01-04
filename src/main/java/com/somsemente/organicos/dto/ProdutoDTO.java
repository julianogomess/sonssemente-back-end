package com.somsemente.organicos.dto;

import com.somsemente.organicos.model.Produto;
import com.somsemente.organicos.model.utils.Tipo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoDTO {
    @NotBlank
    private String nome;
    @NotNull
    private Tipo tipo;
    @PositiveOrZero
    private Double preco;

    public Produto trasnformar(){
        Produto p = new Produto();
        p.setNome(this.getNome());
        p.setTipo(this.getTipo());
        p.setPreco(this.getPreco());
        return p;
    }
}
