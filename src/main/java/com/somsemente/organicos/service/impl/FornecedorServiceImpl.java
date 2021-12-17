package com.somsemente.organicos.service.impl;

import com.somsemente.organicos.model.Fornecedor;
import com.somsemente.organicos.repository.FornecedorRepository;
import com.somsemente.organicos.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorServiceImpl implements FornecedorService {
    @Autowired
    FornecedorRepository fr;

    @Override
    public Fornecedor save(Fornecedor f) {
        return fr.save(f);
    }

    @Override
    public List<Fornecedor> findAll() {
        return fr.findAll();
    }

    @Override
    public Fornecedor findByCnpj(String cnpj) {
        return fr.findByCnpj(cnpj);
    }

    @Override
    public void deleteByCnpj(String cnpj) {
        fr.deleteByCnpj(cnpj);
    }
}
