package org.ai.hospitalmanagementapplicationbackend.service;

import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.MedicalRecordEntity;
import org.ai.hospitalmanagementapplicationbackend.entity.PatientEntity;
import org.ai.hospitalmanagementapplicationbackend.entity.UserEntity;
import org.ai.hospitalmanagementapplicationbackend.exception.HospitalException;
import org.ai.hospitalmanagementapplicationbackend.mapper.DtoConverter;
import org.ai.hospitalmanagementapplicationbackend.repository.MedicalRecordRepository;
import org.ai.hospitalmanagementapplicationbackend.repository.PatientRepository;
import org.ai.hospitalmanagementapplicationbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalRecordImplementation implements MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private  PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Response addMedicalRecord(MedicalRecordEntity medicalRecord) {
        Response response = new Response();
        try {
            // Validate required fields
            if (medicalRecord.getPatient() == null) {
                response.setMessage("Please specify which patient this record belongs to");
                response.setStatusCode(400);
                return response;
            }
            if (medicalRecord.getDiagnosis() == null || medicalRecord.getDiagnosis().trim().isEmpty()) {
                response.setMessage("Diagnosis information is required");
                response.setStatusCode(400);
                return response;
            }

            medicalRecordRepository.save(medicalRecord);
            response.setMessage("Patient's medical record has been successfully created");
            response.setStatusCode(201);
            response.setMedicalRecordDto(DtoConverter.convertMedicalEntityToMedicalDto(medicalRecord));
        } catch (Exception e) {
            response.setMessage("We couldn't create the medical record at this time. Please try again.");
            response.setStatusCode(500);
            // Log the error: log.error("Error adding medical record", e);
        }
        return response;
    }

    @Override
    public Response deleteMedicalRecord(Long id) {
        Response response = new Response();
        try {
            if (medicalRecordRepository.existsById(id)) {
                medicalRecordRepository.deleteById(id);
                response.setMessage("Medical record has been successfully removed");
                response.setStatusCode(200);
            } else {
                response.setMessage("We couldn't find the medical record you're trying to delete");
                response.setStatusCode(404);
            }
        } catch (Exception e) {
            response.setMessage("We couldn't delete the medical record at this time. Please try again.");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response updateMedicalRecord(MedicalRecordEntity updatedRecord, Long id) {
        Response response = new Response();
        try {
            // Validate required fields
            if (updatedRecord.getDiagnosis() == null || updatedRecord.getDiagnosis().trim().isEmpty()) {
                response.setMessage("Diagnosis information is required");
                response.setStatusCode(400);
                return response;
            }

            Optional<MedicalRecordEntity> existingRecord = medicalRecordRepository.findById(id);
            if (existingRecord.isPresent()) {
                MedicalRecordEntity record = existingRecord.get();
                record.setDiagnosis(updatedRecord.getDiagnosis());
                record.setPrescription(updatedRecord.getPrescription());
                record.setNotes(updatedRecord.getNotes());

                medicalRecordRepository.save(record);
                response.setMessage("Patient's medical record has been successfully updated");
                response.setStatusCode(200);
                response.setMedicalRecordDto(DtoConverter.convertMedicalEntityToMedicalDto(record));
            } else {
                response.setMessage("We couldn't find the medical record you're trying to update");
                response.setStatusCode(404);
            }
        } catch (Exception e) {
            response.setMessage("We couldn't update the medical record at this time. Please try again.");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getMedicalRecord(Long id) {
        Response response = new Response();
        try {
            Optional<MedicalRecordEntity> medicalRecord = medicalRecordRepository.findById(id);
            if (medicalRecord.isPresent()) {
                response.setMessage("Medical record retrieved successfully");
                response.setStatusCode(200);
                response.setMedicalRecordDto(DtoConverter.convertMedicalEntityToMedicalDto(medicalRecord.get()));
            } else {
                response.setMessage("We couldn't find the requested medical record");
                response.setStatusCode(404);
            }
        } catch (Exception e) {
            response.setMessage("We're having trouble retrieving the medical record. Please try again.");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getMedicalRecords() {
        Response response = new Response();
        try {
            List<MedicalRecordEntity> records = medicalRecordRepository.findAll();
            if (!records.isEmpty()) {
                response.setMessage("Medical records retrieved successfully");
                response.setStatusCode(200);
                response.setMedicalRecordDtos(DtoConverter.convertMedicalRecordEntityListToMedicalDtoList(records));
            } else {
                response.setMessage("No medical records found in the system");
                response.setStatusCode(200); // Empty list is not an error
            }
        } catch (Exception e) {
            response.setMessage("We're having trouble retrieving medical records. Please try again.");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getMedicalRecordsByPatientId(Long patientId) {
        Response response = new Response();
        try {
            List<MedicalRecordEntity> records = medicalRecordRepository.findByPatientId(patientId);
            if (!records.isEmpty()) {
                response.setMessage("Patient's medical history retrieved successfully");
                response.setStatusCode(200);
                response.setMedicalRecordDtos(DtoConverter.convertMedicalRecordEntityListToMedicalDtoList(records));
            } else {
                response.setMessage("No medical records found for this patient");
                response.setStatusCode(200); // Empty list is not an error
            }
        } catch (Exception e) {
            response.setMessage("We're having trouble retrieving the patient's medical history. Please try again.");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getPatientMedicalRecords(Long patientId, String currentUserEmail) {
        Response response = new Response();
        try {
            // Verify access rights
            if (!hasAccessToRecords(patientId, currentUserEmail)) {
                response.setStatusCode(403);
                response.setMessage("You do not have permission to access this resource");
                return response;
            }

            List<MedicalRecordEntity> records = medicalRecordRepository.findByPatientId(patientId);

            if (records.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No medical records found for this patient");
                return response;
            }

           response.setMessage("Patient's medical history retrieved successfully");
            response.setStatusCode(200);
            return response;

        } catch (HospitalException e) {
            response.setStatusCode(403);
            response.setMessage("You do not have permission to access this resource");

        } catch (Exception e) {
          response.setStatusCode(500);
          response.setMessage("You do not have permission to access this resource");
        }
        return response;
    }

    private boolean hasAccessToRecords(Long patientId, String currentUserEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Admins and doctors have full access
        if (isAdminOrDoctor(authentication)) {
            return true;
        }

        // Patients can only access their own records
        PatientEntity patient = getPatientById(patientId);
        UserEntity user = getUserForPatient(patient);

        return user.getEmail().equals(currentUserEmail);
    }
    private PatientEntity getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new HospitalException("Patient not found with id: " + patientId));
    }

    private UserEntity getUserForPatient(PatientEntity patient) {
        if (patient.getUser() == null) {
            throw new HospitalException("No user associated with patient id: " + patient.getId());
        }

        return userRepository.findById(patient.getUser().getId())
                .orElseThrow(() -> new HospitalException(
                        "User not found for patient id: " + patient.getId()));
    }

    private boolean isAdminOrDoctor(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN") || role.equals("ROLE_DOCTOR"));
    }
}