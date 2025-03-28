package org.ai.hospitalmanagementapplicationbackend.controller;

import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.AdminEntity;
import org.ai.hospitalmanagementapplicationbackend.entity.DoctorEntity;
import org.ai.hospitalmanagementapplicationbackend.entity.StaffEntity;
import org.ai.hospitalmanagementapplicationbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register/doctor")
    public ResponseEntity<Response> doctorRegister(@RequestBody DoctorEntity doctor) throws Exception {
        Response response = authService.RegisterDoctor(doctor);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/register/staff")
    public ResponseEntity<Response> staffRegister(@RequestBody StaffEntity staff) throws Exception {
        Response response = authService.RegisterStaff(staff);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/register/admin")
    public ResponseEntity<Response> adminRegister(@RequestBody AdminEntity admin) throws Exception {
        Response response = authService.RegisterAdmin(admin);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
