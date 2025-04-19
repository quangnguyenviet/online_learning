package com.vitube.online_learning.controller.instructor;

import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.dto.response.RequireResponse;
import com.vitube.online_learning.service.RequireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor-require")
@RequiredArgsConstructor
public class IRequireController {
    private final RequireService requireService;

    @GetMapping("/{courseId}")
    public ApiResponse<List<RequireResponse>> getRequireByCourseId(@PathVariable String courseId) {

        List<RequireResponse> response = requireService.getAllRequireByCourseId(courseId);
        return ApiResponse.<List<RequireResponse>>builder()
                .status(1000)
                .data(response)
                .build();
    }

    @PostMapping("/save")
    public ApiResponse<RequireResponse> saveRequire(@RequestBody RequireResponse requireResponse) {
        RequireResponse response = requireService.saveRequire(requireResponse);
        return ApiResponse.<RequireResponse>builder()
                .status(1000)
                .data(response)
                .build();
    }
}
