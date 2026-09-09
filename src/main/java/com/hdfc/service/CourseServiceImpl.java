package com.hdfc.service;

import com.hdfc.dto.CourseRequestDto;
import com.hdfc.dto.CourseResponseDto;
import com.hdfc.entity.Course;
import com.hdfc.exception.CourseNotFoundException;
import com.hdfc.mapper.CourseMapper;
import com.hdfc.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    private final AtomicInteger idGenerator =
            new AtomicInteger(0);

    @Override
    public CourseResponseDto createCourse(
            CourseRequestDto request) {

        Integer id = idGenerator.incrementAndGet();

        Course course = courseMapper.toEntity(
                request,
                id
        );

        courseRepository.save(course);

        return courseMapper.toResponseDto(course);
    }

    @Override
    public CourseResponseDto getCourseById(
            Integer id) {

        Course course = courseRepository.findById(id);

        if (course == null) {
            throw new CourseNotFoundException(
                    "Course not found with id: " + id
            );
        }

        return courseMapper.toResponseDto(course);
    }

    @Override
    public List<CourseResponseDto> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponseDto updateCourse(
            Integer id,
            CourseRequestDto request) {

        Course existingCourse =
                courseRepository.findById(id);

        if (existingCourse == null) {
            throw new CourseNotFoundException(
                    "Course not found with id: " + id
            );
        }

        existingCourse.setCourseName(
                request.getCourseName()
        );

        existingCourse.setTrainerName(
                request.getTrainerName()
        );

        existingCourse.setDurationInDays(
                request.getDurationInDays()
        );

        existingCourse.setMaxCapacity(
                request.getMaxCapacity()
        );

        existingCourse.setFees(
                request.getFees()
        );

        courseRepository.save(existingCourse);

        return courseMapper.toResponseDto(
                existingCourse
        );
    }

    @Override
    public void deleteCourse(Integer id) {

        Course course =
                courseRepository.findById(id);

        if (course == null) {
            throw new CourseNotFoundException(
                    "Course not found with id: " + id
            );
        }

        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseResponseDto> getCoursesByTrainer(
            String trainerName) {

        return courseRepository.findAll()
                .stream()
                .filter(course ->
                        course.getTrainerName()
                                .equalsIgnoreCase(trainerName)
                )
                .map(courseMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponseDto> getCoursesByFeesLessThan(
            Double amount) {

        return courseRepository.findAll()
                .stream()
                .filter(course ->
                        course.getFees() < amount
                )
                .map(courseMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
