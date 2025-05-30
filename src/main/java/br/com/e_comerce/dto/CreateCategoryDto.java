package br.com.e_comerce.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryDto(
        @NotBlank(message = "Nome da categoria é obrigatório")
        String name
) { }
