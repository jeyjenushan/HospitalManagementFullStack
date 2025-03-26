package org.ai.hospitalmanagementapplicationbackend.controller;


import lombok.AllArgsConstructor;
import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.DoctorEntity;
import org.ai.hospitalmanagementapplicationbackend.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/doctors")
@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    //admin only access
    @DeleteMapping("/admin/doctors/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> deleteDoctor(@PathVariable Long id) {
        Response response = doctorService.deleteDoctor(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Public endpoints
    @GetMapping
    public ResponseEntity<Response> getAllDoctors() {
        Response response = doctorService.fetchAllDoctors();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping(params = "specialization")
    public ResponseEntity<Response> getDoctorsBySpecialization(
            @RequestParam String specialization) {
        Response response = doctorService.getDoctorsBySpecialization(specialization);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping(params = "departmentId")
    public ResponseEntity<Response> getDoctorsByDepartment(
            @RequestParam Long departmentId) {
        Response response = doctorService.getDoctorsByDepartment(departmentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Response> checkDoctorAvailability(
            @PathVariable Long id) {
        Response response = doctorService.checkDoctorAvailability(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Doctor and Admin accessible endpoints
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<Response> updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorEntity updatedDoctor) {
        Response response = doctorService.updateDoctor(id, updatedDoctor);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
