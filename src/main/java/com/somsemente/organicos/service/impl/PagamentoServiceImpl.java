package com.somsemente.organicos.service.impl;

import com.somsemente.organicos.model.Pagamento;
import com.somsemente.organicos.repository.PagamentoRepository;
import com.somsemente.organicos.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    @Autowired
    PagamentoRepository repository;

    @Override
    public Pagamento save(Pagamento pagamento) {
        return repository.save(pagamento);
    }

    @Override
    public boolean deleteById(String id) {
        Optional<Pagamento> p = repository.findById(id);
        if (p.isPresent()){
            repository.delete(p.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Pagamento> findAll() {
        return repository.findAll();
    }

    @Override
    public Pagamento findById(String id) {
        Optional<Pagamento> p = repository.findById(id);
        if (p.isPresent()){
            return p.get();
        }
        return null;
    }

    @Override
    public List<Pagamento> buscaAbertos() {
        return repository.findPagamentoAberto(new Date());
    }

    @Override
    public double somaAbertos() {
        return repository.sumValoremAberto(new Date());
    }
}
