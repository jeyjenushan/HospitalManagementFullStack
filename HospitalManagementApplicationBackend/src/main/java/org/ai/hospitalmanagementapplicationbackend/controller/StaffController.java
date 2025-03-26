package org.ai.hospitalmanagementapplicationbackend.controller;

import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.StaffEntity;
import org.ai.hospitalmanagementapplicationbackend.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/admin/staff")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> addStaff(@RequestBody StaffEntity staff) {
        Response response = staffService.addStaff(staff);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    // Update staff details (Admin only)
    @PutMapping("/admin/staff/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response>  updateStaff(@PathVariable Long id, @RequestBody StaffEntity staffDetails) {
        Response response = staffService.updateStaff(staffDetails,id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

   //Delete staff (Admin only)
    @DeleteMapping("/admin/staff/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        Response response = staffService.deleteStaff(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    // Fetch all staff (Admin only)
    @GetMapping("/staff")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getAllStaff() {
        Response response = staffService.getAllStaff();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    // Fetch staff by role (Admin only)
    @GetMapping(value = "/staff", params = "role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getStaffByRole(@RequestParam String role) {
        Response response = staffService.getStaffByRole(role);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Fetch staff by department (Admin only)
    @GetMapping(value = "/staff", params = "departmentId")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getStaffByDepartment(@RequestParam Long departmentId) {
        Response response = staffService.getStaffByDepartment(departmentId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }




}
