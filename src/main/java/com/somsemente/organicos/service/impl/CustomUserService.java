package com.somsemente.organicos.service.impl;

import com.somsemente.organicos.model.Role;
import com.somsemente.organicos.model.User;
import com.somsemente.organicos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User findByCpf(String cpf){
        return userRepository.findByCpf(cpf);
    }

    public void deleteByCpf(String cpf){
        userRepository.deleteByCpf(cpf);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User save(User user){
        user.setDataCriacao(new Date().toString());
        user.setEnabled(true);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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
