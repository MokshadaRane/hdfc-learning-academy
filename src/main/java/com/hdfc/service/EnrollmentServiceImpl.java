package com.hdfc.service;

import com.hdfc.dto.EnrollmentRequestDto;
import com.hdfc.dto.EnrollmentResponseDto;
import com.hdfc.entity.Course;
import com.hdfc.entity.Enrollment;
import com.hdfc.exception.CourseCapacityFullException;
import com.hdfc.exception.CourseNotFoundException;
import com.hdfc.exception.DuplicateEnrollmentException;
import com.hdfc.exception.EnrollmentNotFoundException;
import com.hdfc.mapper.EnrollmentMapper;
import com.hdfc.repository.CourseRepository;
import com.hdfc.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    private final AtomicInteger idGenerator =
            new AtomicInteger(0);

    @Override
    public EnrollmentResponseDto enrollEmployee(
            EnrollmentRequestDto request) {

        // Rule 1:
        // Employee cannot enroll in a course
        // that does not exist.

        Course course =
                courseRepository.findById(
                        request.getCourseId()
                );

        if (course == null) {
            throw new CourseNotFoundException(
                    "Course not found with id: "
                            + request.getCourseId()
            );
        }

        // Rule 3:
        // Same employee cannot enroll twice
        // in the same course.

        boolean duplicate =
                enrollmentRepository.findAll()
                        .stream()
                        .anyMatch(enrollment ->
                                enrollment.getEmployeeId()
                                        .equals(
                                                request.getEmployeeId()
                                        )
                                        &&
                                        enrollment.getCourseId()
                                                .equals(
                                                        request.getCourseId()
                                                )
                                        &&
                                        !enrollment.getStatus()
                                                .equals("CANCELLED")
                        );

        if (duplicate) {
            throw new DuplicateEnrollmentException(
                    "Employee is already enrolled in this course"
            );
        }

        // Rule 2:
        // Course capacity must not exceed maxCapacity.

        long enrolledCount =
                enrollmentRepository.findAll()
                        .stream()
                        .filter(enrollment ->
                                enrollment.getCourseId()
                                        .equals(
                                                request.getCourseId()
                                        )
                        )
                        .filter(enrollment ->
                                enrollment.getStatus()
                                        .equals("ENROLLED")
                        )
                        .count();

        if (enrolledCount >= course.getMaxCapacity()) {

            throw new CourseCapacityFullException(
                    "Course capacity is full"
            );
        }

        // Create enrollment

        Integer id = idGenerator.incrementAndGet();

        Enrollment enrollment =
                enrollmentMapper.toEntity(
                        request,
                        id
                );

        enrollment.setEnrollmentDate(
                LocalDate.now()
        );

        enrollment.setStatus("ENROLLED");

        enrollmentRepository.save(enrollment);

        return enrollmentMapper.toResponseDto(
                enrollment
        );
    }

    @Override
    public List<EnrollmentResponseDto>
    getAllEnrollments() {

        return enrollmentRepository.findAll()
                .stream()
                .map(enrollmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentResponseDto getEnrollmentById(
            Integer id) {

        Enrollment enrollment =
                enrollmentRepository.findById(id);

        if (enrollment == null) {

            throw new EnrollmentNotFoundException(
                    "Enrollment not found with id: " + id
            );
        }

        return enrollmentMapper.toResponseDto(
                enrollment
        );
    }

    @Override
    public EnrollmentResponseDto cancelEnrollment(
            Integer id) {

        Enrollment enrollment =
                enrollmentRepository.findById(id);

        if (enrollment == null) {

            throw new EnrollmentNotFoundException(
                    "Enrollment not found with id: " + id
            );
        }

        enrollment.setStatus("CANCELLED");

        enrollmentRepository.save(enrollment);

        return enrollmentMapper.toResponseDto(
                enrollment
        );
    }

    @Override
    public EnrollmentResponseDto completeEnrollment(
            Integer id) {

        Enrollment enrollment =
                enrollmentRepository.findById(id);

        if (enrollment == null) {

            throw new EnrollmentNotFoundException(
                    "Enrollment not found with id: " + id
            );
        }

        enrollment.setStatus("COMPLETED");

        enrollmentRepository.save(enrollment);

        return enrollmentMapper.toResponseDto(
                enrollment
        );
    }

    @Override
    public List<EnrollmentResponseDto>
    getEnrollmentsByStatus(String status) {

        return enrollmentRepository.findAll()
                .stream()
                .filter(enrollment ->
                        enrollment.getStatus()
                                .equalsIgnoreCase(status)
                )
                .map(enrollmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentResponseDto>
    getEnrollmentsByEmployeeId(
            Integer employeeId) {

        return enrollmentRepository.findAll()
                .stream()
                .filter(enrollment ->
                        enrollment.getEmployeeId()
                                .equals(employeeId)
                )
                .map(enrollmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public long getEnrollmentCount() {

        return enrollmentRepository.findAll()
                .stream()
                .count();
    }

    @Override
    public String getMostPopularCourse() {

        Map<Integer, Long> courseCounts =
                enrollmentRepository.findAll()
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        Enrollment::getCourseId,
                                        Collectors.counting()
                                )
                        );

        return courseCounts.entrySet()
                .stream()
                .max(
                        Map.Entry.comparingByValue()
                )
                .map(entry -> {

                    Course course =
                            courseRepository.findById(
                                    entry.getKey()
                            );

                    return course != null
                            ? course.getCourseName()
                            : "Unknown Course";

                })
                .orElse("No enrollments available");
    }
}