package org.ai.hospitalmanagementapplicationbackend.controller;


import lombok.AllArgsConstructor;
import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.DepartmentEntity;
import org.ai.hospitalmanagementapplicationbackend.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    //admin end points

    @PostMapping("/admin/departments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> addNewDepartment( @RequestBody DepartmentEntity department) {
        Response response = departmentService.addNewDepartment(department);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/admin/departments/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> updateDepartment(
            @PathVariable Long id,
           @RequestBody DepartmentEntity updatedDepartment) {
        Response response = departmentService.updateDepartment(id, updatedDepartment);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/departments/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> deleteDepartment(@PathVariable Long id) {
        Response response = departmentService.deleteDepartment(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    // Public endpoints

    @GetMapping("/departments")
    public ResponseEntity<Response> getAllDepartments() {
        Response response = departmentService.getAllDepartments();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/departments/{id}/doctors")
    public ResponseEntity<Response> getAllDoctorsByDepartment(@PathVariable Long id) {
        Response response = departmentService.getAllDoctorsByDepartment(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Additional endpoint to get single department (public)
    @GetMapping("/departments/{id}")
    public ResponseEntity<Response> getDepartmentById(@PathVariable Long id) {
        Response response = departmentService.getDepartmentById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }






}
