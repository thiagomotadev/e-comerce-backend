package br.com.e_comerce.dto;

public record RegisterUserDto(
    String name,
    String email,
    String password,
    String comfirmPassword
) { }
