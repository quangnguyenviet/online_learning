package com.vitube.online_learning.controller.instructor;

import org.springframework.web.bind.annotation.*;

import com.vitube.online_learning.dto.request.SaveLearnWhatRequest;
import com.vitube.online_learning.dto.response.ApiResponse;
import com.vitube.online_learning.service.ObjectiveService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/instructor-learn-what")
@RequiredArgsConstructor
public class ILearnWhatController {
    private final ObjectiveService learnWhatService;

    @PatchMapping("/{courseId}")
    public ApiResponse<?> saveLearnWhat(@RequestBody SaveLearnWhatRequest request, @PathVariable String courseId) {
        Object response = learnWhatService.saveLearnWhat(request, courseId);
        return ApiResponse.<Object>builder().status(1000).data(response).build();
    }
}
