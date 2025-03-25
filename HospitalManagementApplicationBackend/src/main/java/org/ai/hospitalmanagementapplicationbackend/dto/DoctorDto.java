package org.ai.hospitalmanagementapplicationbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    private Long id;
    private String name;
    private String specialization;
    private String contactNumber;
    private String availability;
    private Long departmentId;
    private UserDto user;
    private DepartmentDto department;
}
