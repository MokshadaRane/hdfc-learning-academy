package com.hdfc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequestDto {

    @NotNull(message = "Employee ID is required")
    @Positive(message = "Employee ID must be positive")
    private Integer employeeId;

    @NotBlank(message = "Employee name is required")
    private String employeeName;

    @NotNull(message = "Course ID is required")
    @Positive(message = "Course ID must be positive")
    private Integer courseId;
}
