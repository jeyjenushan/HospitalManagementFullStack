package org.ai.hospitalmanagementapplicationbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ai.hospitalmanagementapplicationbackend.enumpack.Role;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private String message;
    private int statusCode;
    private String token;
    private Role role;
    private String expirationTime;
    private AdminDto adminDto;
    private AppointmentDto appointmentDto;
    private DepartmentDto departmentDto;
    private DoctorDto doctorDto;
    private MedicalRecordDto medicalRecordDto;
    private PatientDto patientDto;
    private StaffDto staffDto;
    private UserDto userDto;
    private List<PatientDto>patientDtos;


}
