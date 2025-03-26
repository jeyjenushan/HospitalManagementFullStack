package org.ai.hospitalmanagementapplicationbackend.service;

import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.DepartmentEntity;

public interface DepartmentService {
Response addNewDepartment(DepartmentEntity department);
Response updateDepartment(Long departmentId,DepartmentEntity updatedDepartment);
Response deleteDepartment(Long departmentId);
Response getDepartmentById(Long departmentId);
Response getAllDepartments();
Response getAllDoctorsByDepartment(Long departmentId);



}
