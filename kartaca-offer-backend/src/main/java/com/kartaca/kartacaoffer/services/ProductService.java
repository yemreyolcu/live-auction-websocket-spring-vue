package com.kartaca.kartacaoffer.services;

import com.kartaca.kartacaoffer.entities.Product;
import com.kartaca.kartacaoffer.repositories.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Iterable<Product> productList() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Product productRetrieve(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product productCreate(Product product) {
        return productRepository.save(product);
    }
}
