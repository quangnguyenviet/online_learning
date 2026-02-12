package com.vitube.online_learning.mapper;

import com.vitube.online_learning.dto.CourseDTO;
import com.vitube.online_learning.dto.request.CourseCreattionRequest;
import com.vitube.online_learning.entity.Course;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, LessonMapper.class})
public interface CourseMapper {
//    @Mapping(target = "numberOfLessons", source = "courseEntity")
    CourseDTO toDto(Course courseEntity);

//    default int mapOther(Course courseEntity) {
//        return courseEntity.getLessons() == null ? 0 : courseEntity.getLessons().size();
//    }
    @AfterMapping
    default void afterMapping(
            @MappingTarget CourseDTO.CourseDTOBuilder dto,
            Course course
    ) {
        dto.numberOfLessons(
                course.getLessons() == null ? 0 : course.getLessons().size()
        );
        // Tính toán và định dạng duration
        long totalDurationInSeconds = course.getLessons() == null ? 0 : course.getLessons().stream()
                .mapToLong(lesson -> lesson.getDuration())
                .sum();
        dto.totalDurationInSeconds(totalDurationInSeconds);
        long hours = totalDurationInSeconds / 3600;
        long minutes = (totalDurationInSeconds % 3600) / 60;
        long seconds = totalDurationInSeconds % 60;
        String durationFormatted = String.format("%02dh:%02dm:%02ds", hours, minutes, seconds);
        dto.duration(durationFormatted);

    }

    Course dtoToEntity(CourseDTO dto);


    @Mapping(target = "objectives", ignore = true)
    Course createRequestToEntity(CourseCreattionRequest request);


}
