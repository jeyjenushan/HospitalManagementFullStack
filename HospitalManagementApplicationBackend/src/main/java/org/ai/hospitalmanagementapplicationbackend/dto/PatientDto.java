package org.ai.hospitalmanagementapplicationbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private Long id;
    private int age;
    private String gender;
    private String contactNumber;
    private String medicalHistory;
    private UserDto user;
    private LocalDateTime createdAt;

}
