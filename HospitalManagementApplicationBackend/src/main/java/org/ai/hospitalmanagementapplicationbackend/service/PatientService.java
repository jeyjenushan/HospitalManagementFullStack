package org.ai.hospitalmanagementapplicationbackend.service;

import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.PatientEntity;

public interface PatientService {

    Response getAllPatients();
    Response getPatientById(Long id);
    Response updatePatient(Long id,PatientEntity patientEntity);
    Response deletePatient(Long id);
    Response fetchPatientMedicalHistory(Long id);
 boolean isPatientOwnedByEmail(Long patientId, String email) throws Exception;

}
