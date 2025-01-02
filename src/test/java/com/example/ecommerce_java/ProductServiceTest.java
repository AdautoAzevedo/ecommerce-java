package com.example.ecommerce_java;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ecommerce_java.dtos.ProductDTO;
import com.example.ecommerce_java.exceptions.ResourceNotFoundException;
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
        mockCategory.setId(1L);
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

    @Test
    void getProductById_ShouldThrowException_WhenProductNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getProductById(1L));
    }

    @Test
    void getAllProducts_ShouldReturnProductDTOList() {
        when(repository.findAll()).thenReturn(Arrays.asList(mockProduct));

        List<ProductDTO> result = service.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockProduct.getId(), result.get(0).productId());
    }

    @Test
    void getProductsByCategory_ShouldReturnProductDTOList() {
        when(repository.findByCategoryId(1L)).thenReturn(Arrays.asList(mockProduct));

        List<ProductDTO> result = service.getProductsByCategory(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getProductsByCategory_ShouldThrowException() {
        when(repository.findByCategoryId(1L)).thenReturn(Arrays.asList());

        assertThrows(ResourceNotFoundException.class, () -> service.getProductsByCategory(1L));
    }

    @Test 
    void createProduct_ShouldReturnCreatedProductDTO() {
        when(repository.save(mockProduct)).thenReturn(mockProduct);

        ProductDTO result = service.createProduct(mockProduct);

        assertNotNull(result);
        assertEquals(mockProduct.getId(), result.productId());
        assertEquals(mockProduct.getName(), result.name());
    }

    @Test
    void updateProduct_ShouldUpdateAndReturnProductDTO() {
        when(repository.findById(1L)).thenReturn(Optional.of(mockProduct));
        when(repository.save(any(Product.class))).thenReturn(mockProduct);

        mockProduct.setName("Updated Laptop");
        ProductDTO result = service.updateProduct(mockProduct);

        assertNotNull(result);
        assertEquals("Updated Laptop", result.name());
    }

    @Test
    void deleteProduct_ShouldDeleteProduct() {
        when(repository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteProduct(1L));
        verify(repository, times(1)).deleteById(1L);
    }
}
