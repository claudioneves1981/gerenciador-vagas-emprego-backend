package com.vagasemprego.demo.mappers;

import com.vagasemprego.demo.dtos.UserRequestDTO;
import com.vagasemprego.demo.dtos.UserResponseDTO;
import com.vagasemprego.demo.models.Usuario;

public class UserMapper {

    public static UserResponseDTO toDto(Usuario user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsuario(),
                user.getRole()
        );
    }

    public static Usuario toEntity(UserRequestDTO userDTO) {
        return new Usuario(
                userDTO.username(),
                userDTO.password(),
                userDTO.role()
        );
    }

    public static UserRequestDTO toRequestDto(Usuario user) {
        return new UserRequestDTO(
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}
