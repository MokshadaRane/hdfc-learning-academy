package com.hdfc.controller;

import com.hdfc.service.CourseService;
import com.hdfc.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    // GET /analytics/course-count
    @GetMapping("/course-count")
    public ResponseEntity<Long> getCourseCount() {

        long count = courseService.getAllCourses()
                .stream()
                .count();

        return ResponseEntity.ok(count);
    }

    // GET /analytics/enrollment-count
    @GetMapping("/enrollment-count")
    public ResponseEntity<Long> getEnrollmentCount() {

        return ResponseEntity.ok(
                enrollmentService.getEnrollmentCount()
        );
    }

    // GET /analytics/most-popular-course
    @GetMapping("/most-popular-course")
    public ResponseEntity<String>
    getMostPopularCourse() {

        return ResponseEntity.ok(
                enrollmentService.getMostPopularCourse()
        );
    }
}
