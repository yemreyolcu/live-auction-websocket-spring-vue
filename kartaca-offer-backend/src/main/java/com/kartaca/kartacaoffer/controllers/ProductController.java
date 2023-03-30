package com.kartaca.kartacaoffer.controllers;


import com.kartaca.kartacaoffer.entities.Product;
import com.kartaca.kartacaoffer.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/products")
    public ResponseEntity<?> productList() {
        return ResponseEntity.ok(productService.productList());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> productRetrieve(@PathVariable Long id) {
        Optional<Product> product = Optional.ofNullable(productService.productRetrieve(id));
        if (product.isPresent())
            return ResponseEntity.ok(product.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Product> productCreate(@RequestBody Product product) {
        return ResponseEntity.ok(productService.productCreate(product));
    }
}
