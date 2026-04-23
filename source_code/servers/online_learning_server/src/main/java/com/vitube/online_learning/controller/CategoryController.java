package com.vitube.online_learning.controller;

import com.vitube.online_learning.dto.CategoryDTO;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ApiResponse<?> createCategory(@RequestBody CategoryDTO request) {

        return categoryService.createCategory(request);
    }

    @GetMapping
    public ApiResponse<?> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
