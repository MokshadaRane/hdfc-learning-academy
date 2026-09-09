package com.hdfc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer courseId;
    private String courseName;
    private String trainerName;
    private Integer durationInDays;
    private Integer maxCapacity;
    private Double fees;
}
