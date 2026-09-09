package com.hdfc.controller;

import com.hdfc.dto.CourseRequestDto;
import com.hdfc.dto.CourseResponseDto;
import com.hdfc.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // POST /courses
    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseRequestDto request) {
        return new ResponseEntity<>(
                courseService.createCourse(request),
                HttpStatus.CREATED
        );
    }

    // GET /courses/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                courseService.getCourseById(id)
        );
    }

    // GET /courses
    @GetMapping
    public ResponseEntity<List<CourseResponseDto>>
    getAllCourses() {

        return ResponseEntity.ok(
                courseService.getAllCourses()
        );
    }

    // PUT /courses/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(
            @PathVariable Integer id,
            @Valid @RequestBody CourseRequestDto request) {

        return ResponseEntity.ok(
                courseService.updateCourse(
                        id,
                        request
                )
        );
    }

    // DELETE /courses/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(
            @PathVariable Integer id) {

        courseService.deleteCourse(id);

        return ResponseEntity.ok(
                "Course deleted successfully"
        );
    }

    // GET /courses/trainer/{trainerName}
    @GetMapping("/trainer/{trainerName}")
    public ResponseEntity<List<CourseResponseDto>>
    getCoursesByTrainer(
            @PathVariable String trainerName) {

        return ResponseEntity.ok(
                courseService.getCoursesByTrainer(
                        trainerName
                )
        );
    }

    // GET /courses/fees/{amount}
    @GetMapping("/fees/{amount}")
    public ResponseEntity<List<CourseResponseDto>>
    getCoursesByFeesLessThan(
            @PathVariable Double amount) {

        return ResponseEntity.ok(
                courseService.getCoursesByFeesLessThan(
                        amount
                )
        );
    }
}
