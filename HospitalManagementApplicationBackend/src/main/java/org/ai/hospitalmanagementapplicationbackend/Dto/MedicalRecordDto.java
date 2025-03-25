package org.ai.hospitalmanagementapplicationbackend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordDto {
    private Long id;
    private Long patientId;
    private String patientName;
    private String diagnosis;
    private String prescription;
    private String notes;
    private LocalDateTime createdAt;
}
