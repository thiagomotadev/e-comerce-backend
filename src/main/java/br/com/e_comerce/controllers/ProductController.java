package br.com.e_comerce.controllers;

import br.com.e_comerce.dto.CreateProductDto;
import br.com.e_comerce.dto.ResponseProductDto;
import br.com.e_comerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseProductDto> createProduct(@RequestBody @Valid CreateProductDto dto) {
        var created = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ResponseProductDto>> getAllProducts() {
        var products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
