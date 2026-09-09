package com.hdfc.service;

import com.hdfc.dto.CourseRequestDto;
import com.hdfc.dto.CourseResponseDto;

import java.util.List;

public interface CourseService {

    CourseResponseDto createCourse(CourseRequestDto request);

    CourseResponseDto getCourseById(Integer id);

    List<CourseResponseDto> getAllCourses();

    CourseResponseDto updateCourse(
            Integer id,
            CourseRequestDto request
    );

    void deleteCourse(Integer id);

    List<CourseResponseDto> getCoursesByTrainer(
            String trainerName
    );

    List<CourseResponseDto> getCoursesByFeesLessThan(
            Double amount
    );
}
