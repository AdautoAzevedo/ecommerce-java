package com.example.ecommerce_java.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_java.dtos.ProductDTO;
import com.example.ecommerce_java.dtos.SimplifiedCategoryDTO;
import com.example.ecommerce_java.models.Product;
import com.example.ecommerce_java.repositories.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public ProductDTO getProductById(Long productId) {
        Product product = repository.findById(productId).get();
        return convertToProductDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = repository.findAll();
        return mapProductsList(products);
        
    }

    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        List<Product> products = repository.findByCategory(categoryId);
        return mapProductsList(products);
    }

    public ProductDTO createProduct(Product product) {
        Product productFound =  repository.save(product);
        return convertToProductDTO(productFound);
    }

    public ProductDTO updateProduct(Product correctedProduct) {
        Product product = repository.findById(correctedProduct.getId()).get();
        product.setName(correctedProduct.getName());
        product.setPrice(correctedProduct.getPrice());
        product.setCategory(correctedProduct.getCategory());
        ProductDTO productDTO = convertToProductDTO(repository.save(product));
        return productDTO;
    }

    public void deleteProduct(Long productId) {
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