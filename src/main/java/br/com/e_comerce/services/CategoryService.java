package br.com.e_comerce.services;

import br.com.e_comerce.dto.CreateCategoryDto;
import br.com.e_comerce.dto.ResponseCategoryDto;
import br.com.e_comerce.entities.Category;
import br.com.e_comerce.exceptions.CategoryAlreadyExistsException;
import br.com.e_comerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseCategoryDto create(CreateCategoryDto dto) {

        if (categoryRepository.existsByName(dto.name())) {
            throw new CategoryAlreadyExistsException("Categoria já cadastrada.");
        }

        Category category = new Category(dto.name());
        var categotySaved = category = categoryRepository.save(category);

        return new ResponseCategoryDto(categotySaved);
    }

    public List<ResponseCategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(ResponseCategoryDto::new)
                .toList();
    }
}
