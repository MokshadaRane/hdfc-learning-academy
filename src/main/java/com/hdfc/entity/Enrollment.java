package com.hdfc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    private Integer enrollmentId;
    private Integer employeeId;
    private String employeeName;
    private Integer courseId;
    private LocalDate enrollmentDate;
    private String status;
}
