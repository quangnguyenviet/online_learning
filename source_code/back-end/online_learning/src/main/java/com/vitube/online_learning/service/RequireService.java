package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.response.RequireResponse;
import com.vitube.online_learning.entity.Require;
import com.vitube.online_learning.mapper.RequireMapper;
import com.vitube.online_learning.repository.RequireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequireService {
     private final RequireMapper requireMapper;
     private final RequireRepository requireRepository;
    //
    public List<RequireResponse> getAllRequireByCourseId(String courseId) {
        List<Require> requires = requireRepository.findByCourseId(courseId);
        return requires.stream()
                .map(requireMapper::requireToRequireResponse)
                .collect(Collectors.toList());
    }
}
