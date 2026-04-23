package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.CategoryDTO;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.entity.Category;
import com.vitube.online_learning.mapper.CategoryMapper;
import com.vitube.online_learning.repository.CategoryRepository;
import com.vitube.online_learning.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public ApiResponse<?> createCategory(CategoryDTO request) {
        log.info("Inside createCategory method of CategoryServiceImpl");
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO responseDTO = categoryMapper.toDto(savedCategory);
        return ApiResponse.builder()
                .status(1000)
                .data(responseDTO)
                .build();
    }

    @Override
    public ApiResponse<?> getAllCategories() {
        log.info("Inside getAllCategories method of CategoryServiceImpl");
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(categoryMapper::toDto)
                .toList();
        return ApiResponse.builder()
                .status(1000)
                .data(categoryDTOs)
                .build();
    }
}
