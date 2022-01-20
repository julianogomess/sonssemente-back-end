package com.somsemente.organicos.service;

import com.somsemente.organicos.model.Historico;

import java.util.List;

public interface HistoricoService {
    List<Historico> findAll();
    Historico findById(String id);
    Historico save(Historico historico);
    void delete(Historico historico);
}
