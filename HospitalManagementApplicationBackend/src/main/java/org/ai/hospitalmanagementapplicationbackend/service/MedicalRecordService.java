package org.ai.hospitalmanagementapplicationbackend.service;

import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.MedicalRecordEntity;

public interface MedicalRecordService {
    Response addMedicalRecord(MedicalRecordEntity medicalRecord);
    Response deleteMedicalRecord(Long id);
    Response updateMedicalRecord(MedicalRecordEntity medicalRecord,Long id);
    Response getMedicalRecord(Long id);
    Response getMedicalRecords();
    Response getMedicalRecordsByPatientId(Long patientId);
    Response getPatientMedicalRecords(Long patientId, String currentUserEmail);

}
