package com.hdfc.controller;

import com.hdfc.dto.EnrollmentRequestDto;
import com.hdfc.dto.EnrollmentResponseDto;
import com.hdfc.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    // POST /enrollments
    @PostMapping
    public ResponseEntity<EnrollmentResponseDto> enrollEmployee(@Valid @RequestBody EnrollmentRequestDto request) {
        return new ResponseEntity<>(
                enrollmentService.enrollEmployee(request),
                HttpStatus.CREATED
        );
    }

    // GET /enrollments
    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDto>> getAllEnrollments() {
        return ResponseEntity.ok(
                enrollmentService.getAllEnrollments()
        );
    }

    // GET /enrollments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDto>
    getEnrollmentById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                enrollmentService.getEnrollmentById(id)
        );
    }

    // PUT /enrollments/{id}/cancel
    @PutMapping("/{id}/cancel")
    public ResponseEntity<EnrollmentResponseDto>
    cancelEnrollment(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                enrollmentService.cancelEnrollment(id)
        );
    }

    // PUT /enrollments/{id}/complete
    @PutMapping("/{id}/complete")
    public ResponseEntity<EnrollmentResponseDto>
    completeEnrollment(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                enrollmentService.completeEnrollment(id)
        );
    }

    // GET /enrollments/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EnrollmentResponseDto>>
    getEnrollmentsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                enrollmentService.getEnrollmentsByStatus(
                        status
                )
        );
    }

    // GET /enrollments/employee/{employeeId}
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EnrollmentResponseDto>>
    getEnrollmentsByEmployeeId(
            @PathVariable Integer employeeId) {

        return ResponseEntity.ok(
                enrollmentService.getEnrollmentsByEmployeeId(
                        employeeId
                )
        );
    }
}
