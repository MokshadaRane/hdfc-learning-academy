package com.hdfc.mapper;

import com.hdfc.dto.CourseRequestDto;
import com.hdfc.dto.CourseResponseDto;
import com.hdfc.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public Course toEntity(
            CourseRequestDto dto,
            Integer courseId) {

        return new Course(
                courseId,
                dto.getCourseName(),
                dto.getTrainerName(),
                dto.getDurationInDays(),
                dto.getMaxCapacity(),
                dto.getFees()
        );
    }

    public CourseResponseDto toResponseDto(
            Course course) {

        return new CourseResponseDto(
                course.getCourseId(),
                course.getCourseName(),
                course.getTrainerName(),
                course.getDurationInDays(),
                course.getMaxCapacity(),
                course.getFees()
        );
    }
}
