package com.example.ecommerce_java.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_java.dtos.ProductDTO;
import com.example.ecommerce_java.exceptions.ResourceNotFoundException;
import com.example.ecommerce_java.models.Product;
import com.example.ecommerce_java.repositories.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public ProductDTO getProductDTOById(Long productId) {
        Product product = repository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return DTOMapper.toProductDTO(product);
    }

    public Product getProductById(Long productId) {
        Product product = repository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return product;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = repository.findAll();
        return mapProductsList(products);
        
    }

    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        List<Product> products = repository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found for category ID " + categoryId);
        }
        return mapProductsList(products);
    }

    public ProductDTO createProduct(Product product) {
        Product productFound =  repository.save(product);
        return DTOMapper.toProductDTO(productFound);
    }

    public ProductDTO updateProduct(Product correctedProduct) {
        Product existingProduct = repository.findById(correctedProduct.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        existingProduct.setName(correctedProduct.getName());
        existingProduct.setPrice(correctedProduct.getPrice());
        existingProduct.setCategory(correctedProduct.getCategory());
        ProductDTO productDTO = DTOMapper.toProductDTO(repository.save(existingProduct));
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
                .map(DTOMapper::toProductDTO)
                .collect(Collectors.toList());
    }
}