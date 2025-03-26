package org.ai.hospitalmanagementapplicationbackend.controller;

import lombok.AllArgsConstructor;
import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.MedicalRecordEntity;
import org.ai.hospitalmanagementapplicationbackend.service.MedicalRecordService;
import org.ai.hospitalmanagementapplicationbackend.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;



    // Add medical record (Admin or Doctor)
    @PostMapping("/admin/medical-records")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<Response> addMedicalRecord(
      @RequestBody MedicalRecordEntity medicalRecord) {
        Response response = medicalRecordService.addMedicalRecord(medicalRecord);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Update medical record (Admin or Doctor)
    @PutMapping("/admin/medical-records/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<Response> updateMedicalRecord(
            @PathVariable Long id,
           @RequestBody MedicalRecordEntity updatedRecord) {
        Response response = medicalRecordService.updateMedicalRecord(updatedRecord, id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    // Additional endpoints that might be useful:

    // Get single medical record (Admin or Doctor)
    @GetMapping("/admin/medical-records/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<Response> getMedicalRecord(@PathVariable Long id) {
        Response response = medicalRecordService.getMedicalRecord(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Delete medical record (Admin only)
    @DeleteMapping("/admin/medical-records/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> deleteMedicalRecord(@PathVariable Long id) {
        Response response = medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
