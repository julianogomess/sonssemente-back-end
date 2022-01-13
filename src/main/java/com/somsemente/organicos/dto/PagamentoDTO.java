package com.somsemente.organicos.dto;

import com.somsemente.organicos.model.Pagamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PagamentoDTO {
    @PositiveOrZero
    private Double valor;
    @NotBlank
    private String dataVenc;
    @NotBlank
    private String veiculo;

    public Pagamento transformar() throws ParseException {
        Pagamento p = new Pagamento();
        p.setValor(this.getValor());
        p.setVeiculo(this.getVeiculo());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = format.parse(this.getDataVenc());
        p.setDataVenc(date);
        return p;
    }

}
