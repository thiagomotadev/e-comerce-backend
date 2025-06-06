package br.com.e_comerce.controllers;

import br.com.e_comerce.dto.CreateCategoryDto;
import br.com.e_comerce.dto.ResponseCategoryDto;
import br.com.e_comerce.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseCategoryDto> createCategory(@RequestBody @Valid CreateCategoryDto dto) {
        var created = categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDto>> listCategories() {
        var categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }
}
