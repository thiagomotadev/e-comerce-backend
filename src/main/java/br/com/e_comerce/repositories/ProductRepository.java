package br.com.e_comerce.repositories;

import br.com.e_comerce.entities.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    List<Product> findByCategoryId(Long categoryId);
}
