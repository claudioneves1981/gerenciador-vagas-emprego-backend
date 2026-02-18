package com.vagasemprego.demo.dtos;

import com.vagasemprego.demo.models.Usuario;

public record UserRequestDTO(

        String username,
        String password,
        Usuario.Role role

) {
}