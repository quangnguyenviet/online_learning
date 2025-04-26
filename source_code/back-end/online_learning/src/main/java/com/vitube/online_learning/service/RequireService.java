package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.request.SaveRequireRequest;
import com.vitube.online_learning.dto.response.RequireResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Require;
import com.vitube.online_learning.mapper.RequireMapper;
import com.vitube.online_learning.repository.CourseRepository;
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
     private final CourseRepository courseRepository;
    //
    public List<RequireResponse> getAllRequireByCourseId(String courseId) {
        List<Require> requires = requireRepository.findByCourseId(courseId);
        return requires.stream()
                .map(requireMapper::requireToRequireResponse)
                .collect(Collectors.toList());
    }
    public RequireResponse saveRequire(SaveRequireRequest request, String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        List<Require> requires = requireRepository.findByCourseId(courseId);
        requires.forEach(require -> {
            request.getDelIdList().forEach(delId -> {
                requireRepository.deleteById(delId);
            });
        });
        request.getOtherList().forEach(other -> {
            if(other.get("id") == null){
                Require require = new Require();
                require.setCourse(course);
                require.setDescription(other.get("description").toString());
                course.getRequires().add(require);
                requireRepository.save(require);
            }
            else{
                Require require = requireRepository.findById(other.get("id").toString())
                        .orElseThrow(() -> new RuntimeException("Require not found"));
                require.setDescription(other.get("description").toString());
                requireRepository.save(require);
            }

        });



        return null;
    }
}
