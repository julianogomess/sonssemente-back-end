package com.somsemente.organicos.dto;

import com.somsemente.organicos.model.Historico;
import com.somsemente.organicos.model.User;
import com.somsemente.organicos.model.utils.Tipo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class HistoricoDTO {
    @NotBlank(message = "O email do usuário que realizou a pesquisa é necessário")
    private String email;
    @NotBlank(message = "Qual foi o item ou tipo de produto pesquisado")
    private String pesquisa;
    @NotNull(message = "É necessário registrar se o usuário finalizou a compra ou não")
    private boolean realizado;

    public Historico transformar( Tipo tipo, String nome){
        Historico h = new Historico();
        h.setEmail(this.getEmail());
        h.setPesquisa(nome);
        h.setData(new Date());
        h.setTipo(tipo);
        h.setRealizado(this.realizado);
        return h;
    }
}
