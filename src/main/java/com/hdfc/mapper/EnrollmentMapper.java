package com.hdfc.mapper;

import com.hdfc.dto.EnrollmentRequestDto;
import com.hdfc.dto.EnrollmentResponseDto;
import com.hdfc.entity.Enrollment;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    public Enrollment toEntity(
            EnrollmentRequestDto dto,
            Integer enrollmentId) {

        return new Enrollment(
                enrollmentId,
                dto.getEmployeeId(),
                dto.getEmployeeName(),
                dto.getCourseId(),
                null,
                null
        );
    }

    public EnrollmentResponseDto toResponseDto(
            Enrollment enrollment) {

        return new EnrollmentResponseDto(
                enrollment.getEnrollmentId(),
                enrollment.getEmployeeId(),
                enrollment.getEmployeeName(),
                enrollment.getCourseId(),
                enrollment.getEnrollmentDate(),
                enrollment.getStatus()
        );
    }
}
