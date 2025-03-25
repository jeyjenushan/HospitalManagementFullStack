package org.ai.hospitalmanagementapplicationbackend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {
    private Long id;
    private String role;
    private String contactNumber;
    private Long departmentId;
    private UserDto userDto;
}
