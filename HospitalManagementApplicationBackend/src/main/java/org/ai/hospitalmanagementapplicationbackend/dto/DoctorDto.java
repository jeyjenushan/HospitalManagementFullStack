package org.ai.hospitalmanagementapplicationbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    private Long id;
    private String specialization;
    private String contactNumber;
    private String availability;
    private UserDto user;
    private DepartmentDto department;
}
