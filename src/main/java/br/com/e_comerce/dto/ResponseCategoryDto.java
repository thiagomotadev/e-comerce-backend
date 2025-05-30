package br.com.e_comerce.dto;

import br.com.e_comerce.entities.Category;

public record ResponseCategoryDto(
        Long id,
        String name
) {
    public ResponseCategoryDto(Category category) {
        this(category.getId(), category.getName());
    }
}
