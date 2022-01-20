package com.somsemente.organicos.service.impl;

import com.somsemente.organicos.model.Historico;
import com.somsemente.organicos.repository.HistoricoRepository;
import com.somsemente.organicos.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoricoServiceImpl implements HistoricoService {

    @Autowired
    HistoricoRepository repository;

    @Override
    public List<Historico> findAll() {
        return repository.findAll();
    }

    @Override
    public Historico findById(String id) {
        Optional<Historico> h = repository.findById(id);
        if(h.isPresent()){
            return h.get();
        }
        return null;
    }

    @Override
    public Historico save(Historico historico) {
        return repository.save(historico);
    }

    @Override
    public void delete(Historico historico) {
        repository.delete(historico);
    }
}
