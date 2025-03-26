package org.ai.hospitalmanagementapplicationbackend.controller;

import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.AppointmentEntity;
import org.ai.hospitalmanagementapplicationbackend.enumpack.AppointmentStatus;
import org.ai.hospitalmanagementapplicationbackend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Book a new appointment (Patient only)
    @PostMapping("/appointments")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Response> bookAppointment(@RequestBody AppointmentEntity appointment) {
        Response response = appointmentService.bookAppointment(appointment);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Reschedule an appointment (Patient or Admin)
    @PutMapping("/appointments/{id}/reschedule")
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    public ResponseEntity<Response> rescheduleAppointment(
            @PathVariable Long id,
          @RequestBody AppointmentEntity appointmentDetails) {
        Response response = appointmentService.rescheduleAppointment(appointmentDetails, id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Cancel an appointment (Patient or Admin)
    @DeleteMapping("/appointments/{id}")
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    public ResponseEntity<Response> cancelAppointment(@PathVariable Long id) {
        Response response = appointmentService.cancelAppointment(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Get appointments for a specific patient (Patient or Admin)
    @GetMapping("/patients/{id}/appointments")
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    public ResponseEntity<Response> getPatientAppointments(@PathVariable Long id) {
        Response response = appointmentService.getAppointmentsForPatient(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Get appointments for a specific doctor (Doctor or Admin)
    @GetMapping("/doctors/{id}/appointments")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<Response> getDoctorAppointments(@PathVariable Long id) {
        Response response = appointmentService.getAppointmentsForDoctor(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Get appointments by status (Admin only)
    @GetMapping("/appointments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getAppointmentsByStatus(
            @RequestParam AppointmentStatus status) {
        Response response = appointmentService.getAppointmentsByStatus(status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
