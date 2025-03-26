package org.ai.hospitalmanagementapplicationbackend.service;

import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.StaffEntity;

public interface StaffService {
    Response addStaff(StaffEntity staffEntity);
    Response updateStaff(StaffEntity staffEntity,Long id);
    Response deleteStaff(Long id);
    Response getStaff(Long id);
    Response getAllStaff();
    Response getStaffByRole(String role);
    Response getStaffByDepartment(Long departmentId);
}
