package com.example.ecommerce_java.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_java.dtos.ProductDTO;
import com.example.ecommerce_java.dtos.SimplifiedCategoryDTO;
import com.example.ecommerce_java.exceptions.ResourceNotFoundException;
import com.example.ecommerce_java.models.Product;
import com.example.ecommerce_java.repositories.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public ProductDTO getProductById(Long productId) {
        Product product = repository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return convertToProductDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = repository.findAll();
        return mapProductsList(products);
        
    }

    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        List<Product> products = repository.findByCategory(categoryId);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for category ID " + categoryId);
        }
        return mapProductsList(products);
    }

    public ProductDTO createProduct(Product product) {
        Product productFound =  repository.save(product);
        return convertToProductDTO(productFound);
    }

    public ProductDTO updateProduct(Product correctedProduct) {
        Product existingProduct = repository.findById(correctedProduct.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        existingProduct.setName(correctedProduct.getName());
        existingProduct.setPrice(correctedProduct.getPrice());
        existingProduct.setCategory(correctedProduct.getCategory());
        ProductDTO productDTO = convertToProductDTO(repository.save(existingProduct));
        return productDTO;
    }

    public void deleteProduct(Long productId) {
        if (!repository.existsById(productId)) {
            throw new ResourceNotFoundException("Product with Id" + productId);
        }
        repository.deleteById(productId);
    }

    private List<ProductDTO> mapProductsList(List<Product> products) {
        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToProductDTO(Product product) {
        SimplifiedCategoryDTO categoryDTO = new SimplifiedCategoryDTO(product.getCategory().getCategoryId(), product.getCategory().getCategoryName());
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), categoryDTO);
    }

}