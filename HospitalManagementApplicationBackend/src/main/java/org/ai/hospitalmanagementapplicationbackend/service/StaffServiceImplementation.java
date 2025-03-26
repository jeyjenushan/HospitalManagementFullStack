package org.ai.hospitalmanagementapplicationbackend.service;

import lombok.AllArgsConstructor;
import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.DepartmentEntity;
import org.ai.hospitalmanagementapplicationbackend.entity.StaffEntity;
import org.ai.hospitalmanagementapplicationbackend.entity.UserEntity;
import org.ai.hospitalmanagementapplicationbackend.mapper.DtoConverter;
import org.ai.hospitalmanagementapplicationbackend.repository.DepartmentRepository;
import org.ai.hospitalmanagementapplicationbackend.repository.StaffRepository;
import org.ai.hospitalmanagementapplicationbackend.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class StaffServiceImplementation implements StaffService {

    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public Response addStaff(StaffEntity staffEntity) {
        Response response = new Response();
        try {
            // Validate required fields before saving
            if (staffEntity.getStaffrole() == null || staffEntity.getStaffrole().isEmpty()) {
                response.setMessage("Staff role is required");
                response.setStatusCode(400); // Bad Request
                return response;
            }

            StaffEntity savedStaff = staffRepository.save(staffEntity);
            response.setMessage("Staff member added successfully");
            response.setStatusCode(201); // Created
            response.setStaffDto(DtoConverter.convertStaffEntityToStaffDto(savedStaff));
        } catch (DataIntegrityViolationException e) {
            response.setMessage("Could not add staff: Missing required information or duplicate entry");
            response.setStatusCode(400); // Bad Request
        } catch (Exception e) {
            response.setMessage("An unexpected error occurred while adding staff");
            response.setStatusCode(500); // Internal Server Error
        }
        return response;
    }

    @Override
    public Response updateStaff(StaffEntity staffEntity, Long id) {
        Response response = new Response();
        try {
            if (id == null) {
                response.setMessage("Staff ID is required for update");
                response.setStatusCode(400);
                return response;
            }

            Optional<StaffEntity> existingStaffOpt = staffRepository.findById(id);
            if (!existingStaffOpt.isPresent()) {
                response.setMessage("Staff member with ID " + id + " not found");
                response.setStatusCode(404);
                return response;
            }

            StaffEntity existingStaff = existingStaffOpt.get();

            // Update only non-null fields from the input
            if (staffEntity.getStaffrole() != null) {
                existingStaff.setStaffrole(staffEntity.getStaffrole());
            }
            if (staffEntity.getContactNumber() != null) {
                existingStaff.setContactNumber(staffEntity.getContactNumber());
            }

            // Update User if provided
            if (staffEntity.getUser() != null && staffEntity.getUser().getId() != null) {
                Optional<UserEntity> userOpt = userRepository.findById(staffEntity.getUser().getId());
                if (userOpt.isPresent()) {
                    existingStaff.setUser(userOpt.get());
                } else {
                    response.setMessage("User with ID " + staffEntity.getUser().getId() + " not found");
                    response.setStatusCode(404);
                    return response;
                }
            }

            // Update Department if provided
            if (staffEntity.getDepartment() != null && staffEntity.getDepartment().getId() != null) {
                Optional<DepartmentEntity> departmentOpt = departmentRepository.findById(staffEntity.getDepartment().getId());
                if (departmentOpt.isPresent()) {
                    existingStaff.setDepartment(departmentOpt.get());
                } else {
                    response.setMessage("Department with ID " + staffEntity.getDepartment().getId() + " not found");
                    response.setStatusCode(404);
                    return response;
                }
            }

            staffRepository.save(existingStaff);
            response.setMessage("Staff details updated successfully");
            response.setStatusCode(200);
            response.setStaffDto(DtoConverter.convertStaffEntityToStaffDto(existingStaff));
        } catch (Exception e) {
            response.setMessage("Could not update staff details. Please try again.");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response deleteStaff(Long id) {
        Response response = new Response();
        try {
            if (id == null) {
                response.setMessage("Staff ID is required for deletion");
                response.setStatusCode(400);
                return response;
            }

            if (!staffRepository.existsById(id)) {
                response.setMessage("Staff member with ID " + id + " not found");
                response.setStatusCode(404);
                return response;
            }

            staffRepository.deleteById(id);
            response.setMessage("Staff member removed successfully");
            response.setStatusCode(200);
        } catch (DataIntegrityViolationException e) {
            response.setMessage("Cannot delete staff member as they are associated with other records");
            response.setStatusCode(409); // Conflict
        } catch (Exception e) {
            response.setMessage("Could not remove staff member. Please try again.");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getStaff(Long id) {
        Response response = new Response();
        try {
            if (id == null) {
                response.setMessage("Staff ID is required");
                response.setStatusCode(400);
                return response;
            }

            Optional<StaffEntity> staff = staffRepository.findById(id);
            if (staff.isPresent()) {
                response.setMessage("Staff details retrieved successfully");
                response.setStatusCode(200);
                response.setStaffDto(DtoConverter.convertStaffEntityToStaffDto(staff.get()));
            } else {
                response.setMessage("Staff member with ID " + id + " not found");
                response.setStatusCode(404);
            }
        } catch (Exception e) {
            response.setMessage("Could not retrieve staff details. Please try again.");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getAllStaff() {
        Response response = new Response();
        try {
            List<StaffEntity> staffList = staffRepository.findAll();
            if (staffList.isEmpty()) {
                response.setMessage("No staff members found");
                response.setStatusCode(200);
            } else {
                response.setMessage(staffList.size() + " staff members retrieved successfully");
                response.setStatusCode(200);
                response.setStaffDtos(DtoConverter.convertStaffEntityListToStaffDtoList(staffList));
            }
        } catch (Exception e) {
            response.setMessage("Could not retrieve staff list. Please try again.");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getStaffByRole(String role) {
        Response response = new Response();
        try {
            if (role == null || role.trim().isEmpty()) {
                response.setMessage("Role is required");
                response.setStatusCode(400);
                return response;
            }

            List<StaffEntity> staffByRole = staffRepository.findByStaffrole(role);
            if (staffByRole.isEmpty()) {
                response.setMessage("No staff members found with role: " + role);
                response.setStatusCode(200);
            } else {
                response.setMessage(staffByRole.size() + " staff members with role '" + role + "' retrieved successfully");
                response.setStatusCode(200);
                response.setStaffDtos(DtoConverter.convertStaffEntityListToStaffDtoList(staffByRole));
            }
        } catch (Exception e) {
            response.setMessage("Could not search staff by role. Please try again.");
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response getStaffByDepartment(Long departmentId) {
        Response response = new Response();
        try {
            if (departmentId == null) {
                response.setMessage("Department ID is required");
                response.setStatusCode(400);
                return response;
            }

            Optional<DepartmentEntity> department = departmentRepository.findById(departmentId);
            if (!department.isPresent()) {
                response.setMessage("Department with ID " + departmentId + " not found");
                response.setStatusCode(404);
                return response;
            }

            List<StaffEntity> staffByDepartment = staffRepository.findByDepartment(department.get());
            if (staffByDepartment.isEmpty()) {
                response.setMessage("No staff members found in this department");
                response.setStatusCode(200);
            } else {
                response.setMessage(staffByDepartment.size() + " staff members in department retrieved successfully");
                response.setStatusCode(200);
                response.setStaffDtos(DtoConverter.convertStaffEntityListToStaffDtoList(staffByDepartment));
            }
        } catch (Exception e) {
            response.setMessage("Could not search staff by department. Please try again.");
            response.setStatusCode(500);
        }
        return response;
    }
}