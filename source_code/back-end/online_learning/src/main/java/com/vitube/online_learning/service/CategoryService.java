package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.CategoryDTO;
import com.vitube.online_learning.dto.response.ApiResponse;

public interface CategoryService {
    ApiResponse<?> createCategory(CategoryDTO request);
    ApiResponse<?> getAllCategories();
}
