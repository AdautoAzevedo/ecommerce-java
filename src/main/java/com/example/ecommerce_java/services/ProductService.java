package com.example.ecommerce_java.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_java.models.Product;
import com.example.ecommerce_java.repositories.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product getProductById(Long productId) {
        return repository.findById(productId).get();
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return repository.findByCategory(categoryId);
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Product correctedProduct) {
        Product product = repository.findById(correctedProduct.getId()).get();
        product.setName(correctedProduct.getName());
        product.setPrice(correctedProduct.getPrice());
        product.setCategory(correctedProduct.getCategory());
        return repository.save(product);
    }

    public void deleteProduct(Long productId) {
        repository.deleteById(productId);
    }
}
