package com.hrabrov.electronic_device_catalog.repositories;

import com.hrabrov.electronic_device_catalog.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Product, Long> {
    List<Product> findByCategory(String category);
    Product findById(Integer id);
}
