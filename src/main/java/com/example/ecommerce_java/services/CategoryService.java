package com.example.ecommerce_java.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce_java.dtos.CategoryDTO;
import com.example.ecommerce_java.dtos.SimplifiedCategoryDTO;
import com.example.ecommerce_java.exceptions.ResourceNotFoundException;
import com.example.ecommerce_java.models.Category;
import com.example.ecommerce_java.repositories.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = repository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return new CategoryDTO(category.getCategoryName());
    }

    public List<CategoryDTO> getAllCategories() {
        System.out.println("Called");
        return repository.findAll().stream()
                .map(category -> new CategoryDTO(category.getCategoryName()))
                .collect(Collectors.toList());
    }

    public SimplifiedCategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryName(categoryDTO.name());
        Category savedCategory = repository.save(category);
        return new SimplifiedCategoryDTO(savedCategory.getId(), savedCategory.getCategoryName());
    }

    public void deleteCategory(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        repository.deleteById(id);
    }
}
