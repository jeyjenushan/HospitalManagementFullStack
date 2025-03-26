package org.ai.hospitalmanagementapplicationbackend.controller;

import lombok.AllArgsConstructor;
import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.PatientEntity;
import org.ai.hospitalmanagementapplicationbackend.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    //admin only
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<Response> getAllPatients(){
        Response response=patientService.getAllPatients();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    //admin only or patient them selves
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PATIENT') and @patientRepository.findById(#id).get().getUser().email == principal.email)")
    public ResponseEntity<Response> getPatientById(@PathVariable Long id){
        Response response=patientService.getPatientById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    //admin or patient
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PATIENT') and @patientRepository.findById(#id).get().getUser().email == principal.email)")
    public ResponseEntity<Response> updatePatient(@PathVariable Long id,
                                                  @RequestBody PatientEntity patientEntity){
        Response response=patientService.updatePatient(id,patientEntity);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    //Admin Only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> deletePatient(@PathVariable Long id){
        Response response=patientService.deletePatient(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }





    //Admin Only or person them selves
    //Not check Yet
    @GetMapping("/{id}/medical-records")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or (hasRole('PATIENT') and #id == principal.id)")
    public ResponseEntity<Response> fetchPatientMedicalRecords(@PathVariable Long id){
        Response response=patientService.fetchPatientMedicalHistory(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
