package org.ai.hospitalmanagementapplicationbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
private Long id;
private String name;
private String description;
private List<DoctorDto> doctors;


}
