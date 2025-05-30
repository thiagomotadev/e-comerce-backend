package br.com.e_comerce.services;

import br.com.e_comerce.dto.CreateCategoryDto;
import br.com.e_comerce.dto.ResponseCategoryDto;
import br.com.e_comerce.entities.Category;
import br.com.e_comerce.repositories.CategoryRepository;
import java.lang.RuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseCategoryDto create(CreateCategoryDto dto) {
        if (categoryRepository.existsByName(dto.name())) {
            throw new RuntimeException("Categoria já cadastrada.");
        }

        Category category = Category.builder().name(dto.name()).build();
        category = categoryRepository.save(category);

        return new ResponseCategoryDto(category);
    }

    public List<ResponseCategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(ResponseCategoryDto::new)
                .toList();
    }
}
