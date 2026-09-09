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
public class CourseRequestDto {

    @NotBlank(message = "Course name is required")
    private String courseName;

    @NotBlank(message = "Trainer name is required")
    private String trainerName;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than zero")
    private Integer durationInDays;

    @NotNull(message = "Maximum capacity is required")
    @Positive(message = "Maximum capacity must be greater than zero")
    private Integer maxCapacity;

    @NotNull(message = "Fees are required")
    @Positive(message = "Fees must be positive")
    private Double fees;
}
