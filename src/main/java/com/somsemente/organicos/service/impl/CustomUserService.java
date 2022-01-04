package com.somsemente.organicos.service.impl;

import com.somsemente.organicos.dto.UserDTO;
import com.somsemente.organicos.model.utils.Role;
import com.somsemente.organicos.model.User;
import com.somsemente.organicos.repository.UserRepository;
import com.somsemente.organicos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Primary
public class CustomUserService implements UserDetailsService, UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    @Override
    public User findByCpf(String cpf){
        return userRepository.findByCpf(cpf);
    }
    @Override
    public void deleteByCpf(String cpf){
        userRepository.deleteByCpf(cpf);
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }
    @Override
    public User save(User user){
        user.setDataCriacao(new Date());
        user.setEnabled(true);
        Set<Role> roles = new HashSet<>();
        if(user.getRoles()!=null) {
            roles = user.getRoles();
        }
        roles.add(Role.USER);
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public User atualizar(User u, UserDTO dto){
        u.setDataNasc(dto.getDataNasc());
        u.setEmail(dto.getEmail());
        u.setNome(dto.getNome());
        u.setCpf(dto.getCpf());
        u.setTelefone(dto.getTelefone());
        u.setEndereco(dto.getEndereco());
        u.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        return userRepository.save(u);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email);
        if(user != null){
            return buildUserForAuthentication(user,getAuthorities(user.getRoles()));
        }else {
            throw new UsernameNotFoundException("Email nao encontrado");
        }
    }

    public List<GrantedAuthority> getAuthorities(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.toString()));
        });
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }


}
