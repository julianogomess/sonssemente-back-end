package com.somsemente.organicos.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

;import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Pagamentos")
@ApiModel(value = "Pagamento")
public class Pagamento {
    @Id
    private String id;
    private Double valor;
    private Date dataVenc;
    private String veiculo;

}
