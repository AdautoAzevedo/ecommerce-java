package com.example.ecommerce_java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ecommerce_java.dtos.ProductDTO;
import com.example.ecommerce_java.models.Category;
import com.example.ecommerce_java.models.Product;
import com.example.ecommerce_java.repositories.ProductRepository;
import com.example.ecommerce_java.services.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    private Product mockProduct;

    private Category mockCategory;

    @BeforeEach
    void setUp() {
        mockCategory = new Category();
        mockCategory.setCategoryId(1L);
        mockCategory.setCategoryName("mock category");

        mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setName("mock product");
        mockProduct.setPrice(BigDecimal.valueOf(300));
        mockProduct.setCategory(mockCategory);
    }

    @Test
    void getProductsById_ShouldReturnProductDTO(){
        when(repository.findById(1L)).thenReturn(Optional.of(mockProduct));
        
        ProductDTO result = service.getProductById(1L);
        
        assertNotNull(result);
        assertEquals(mockProduct.getId(), result.productId());
        assertEquals(mockProduct.getName(), result.name());
    }

}
