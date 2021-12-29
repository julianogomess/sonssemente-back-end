package com.somsemente.organicos.service;

import com.somsemente.organicos.dto.FornecedorDTO;
import com.somsemente.organicos.model.Fornecedor;

import java.util.List;

public interface FornecedorService {
    Fornecedor save(Fornecedor f);
    List<Fornecedor> findAll();
    Fornecedor findByCnpj(String cnpj);
    void deleteByCnpj(String cnpj);
    Fornecedor atualizar(Fornecedor f, FornecedorDTO dto);
}
