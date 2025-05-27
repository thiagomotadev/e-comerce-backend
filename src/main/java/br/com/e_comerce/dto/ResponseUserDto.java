package br.com.e_comerce.dto;

import java.time.LocalDateTime;

import br.com.e_comerce.entities.enums.Role;

public record ResponseUserDto(
    String name,
    String email,
    Role role,
    LocalDateTime createdAt
) {

}
