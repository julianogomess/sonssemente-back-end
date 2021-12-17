package com.somsemente.organicos.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthBody {
    private String email;
    private String password;
}