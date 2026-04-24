package com.vitube.online_learning.controller;

import com.vitube.online_learning.dto.CategoryDTO;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Tag(name = "Category Management", description = "APIs for managing course categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Create a new category", description = "Admin only. Creates a new course category in the system.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Admin access required", content = @Content)
    })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ApiResponse<?> createCategory(@RequestBody CategoryDTO request) {
        return categoryService.createCategory(request);
    }

    @Operation(summary = "Get all categories", description = "Retrieves a list of all available course categories.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories")
    })
    @GetMapping
    public ApiResponse<?> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
