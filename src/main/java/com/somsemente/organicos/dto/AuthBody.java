package com.somsemente.organicos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AuthBody {
    @NotNull
    private String email;
    @NotNull
    private String password;
}