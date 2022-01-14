package com.somsemente.organicos.service;


import com.somsemente.organicos.model.Pagamento;

import java.util.List;

public interface PagamentoService {
    Pagamento save(Pagamento pagamento);
    boolean deleteById(String id);
    List<Pagamento> findAll();
    Pagamento findById(String id);
    List<Pagamento> buscaAbertos();
    double somaAbertos();

}
