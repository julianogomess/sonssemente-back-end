package com.somsemente.organicos.service;

import com.somsemente.organicos.dto.UserDTO;
import com.somsemente.organicos.model.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    User findByCpf(String cpf);
    void deleteByCpf(String cpf);
    List<User> findAll();
    User save(User user);
    User atualizar(User u, UserDTO dto);
}
