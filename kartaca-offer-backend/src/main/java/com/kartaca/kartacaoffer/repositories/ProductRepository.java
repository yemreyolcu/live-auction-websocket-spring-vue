package com.kartaca.kartacaoffer.repositories;

import com.kartaca.kartacaoffer.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}