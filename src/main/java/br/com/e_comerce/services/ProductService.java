package br.com.e_comerce.services;

import br.com.e_comerce.dto.CreateProductDto;
import br.com.e_comerce.dto.ResponseProductDto;
import br.com.e_comerce.entities.Category;
import br.com.e_comerce.entities.Product;
import br.com.e_comerce.repositories.CategoryRepository;
import br.com.e_comerce.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ResponseProductDto createProduct(CreateProductDto dto) {
        // Validar se a categoria existe
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // Validar preço e estoque
        if (dto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Preço não pode ser negativo");
        }
        if (dto.getStock() < 0) {
            throw new RuntimeException("Estoque não pode ser negativo");
        }

        // Montar a entidade Product
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);
        product.setImageUrl(dto.getImageUrl());

        // Salvar no banco
        product = productRepository.save(product);

        // Retornar ResponseProductDto
        return new ResponseProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getName(),
                product.getImageUrl());
    }

    public List<ResponseProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(p -> new ResponseProductDto(
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getPrice(),
                        p.getStock(),
                        p.getCategory().getName(),
                        p.getImageUrl()))
                .collect(Collectors.toList());
    }
    // Outros métodos (ex: buscar por categoria)
}
