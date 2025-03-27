package org.ai.hospitalmanagementapplicationbackend.service;

import org.ai.hospitalmanagementapplicationbackend.dto.DepartmentDto;
import org.ai.hospitalmanagementapplicationbackend.dto.DoctorDto;
import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.DepartmentEntity;
import org.ai.hospitalmanagementapplicationbackend.entity.DoctorEntity;
import org.ai.hospitalmanagementapplicationbackend.mapper.DtoConverter;
import org.ai.hospitalmanagementapplicationbackend.repository.DepartmentRepository;
import org.ai.hospitalmanagementapplicationbackend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImplementation implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;


    @Override
    public Response addNewDepartment(DepartmentEntity department) {
        Response response = new Response();
        try {
            if (department.getName() == null || department.getName().trim().isEmpty()) {
                response.setMessage("Department name cannot be empty");
                response.setStatusCode(400);
                return response;
            }
            DepartmentEntity savedDepartment=departmentRepository.save(department);
            response.setMessage("Department added successfully");
            response.setStatusCode(201);
            response.setDepartmentDto(DtoConverter.convertDepartmentEntityToDepartmentDto(savedDepartment));
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }

    @Override
    public Response updateDepartment(Long departmentId, DepartmentEntity updatedDepartment) {
        Response response = new Response();
        try {
            if (updatedDepartment.getName() == null || updatedDepartment.getName().trim().isEmpty()) {
                response.setMessage("Department name cannot be empty");
                response.setStatusCode(400);
                return response;
            }

            Optional<DepartmentEntity> existingDepartment = departmentRepository.findById(departmentId);
            if (existingDepartment.isPresent()) {
                DepartmentEntity department = existingDepartment.get();
                department.setName(updatedDepartment.getName());
                department.setDescription(updatedDepartment.getDescription());

                departmentRepository.save(department);
                response.setMessage("Department information updated successfully");
                response.setStatusCode(200);
                response.setDepartmentDto(DtoConverter.convertDepartmentEntityToDepartmentDto(department));
            } else {
                response.setMessage("We couldn't find the department you're trying to update");
                response.setStatusCode(404);
            }
        } catch (Exception e) {
            response.setMessage("We couldn't update the department information at this time. Please try again.");
            response.setStatusCode(500);
        }
        return response;
    }


    @Override
    public Response deleteDepartment(Long departmentId) {
        Response response = new Response();
        try {
            if (departmentRepository.existsById(departmentId)) {
                // Check if department has doctors before deleting
                if (!departmentRepository.findById(departmentId).get().getDoctors().isEmpty()) {
                    response.setMessage("Cannot delete department because it has doctors assigned. Please reassign doctors first.");
                    response.setStatusCode(400);
                    return response;
                }

                departmentRepository.deleteById(departmentId);
                response.setMessage("Department removed successfully");
                response.setStatusCode(200);
            } else {
                response.setMessage("We couldn't find the department you're trying to remove");
                response.setStatusCode(404);
            }
        } catch (Exception e) {
            response.setMessage("We couldn't remove the department at this time. Please try again.");
            response.setStatusCode(500);
            // Log the actual error for debugging: log.error("Error deleting department", e);
        }
        return response;
    }

    @Override
    public Response getDepartmentById(Long departmentId) {
        Response response = new Response();
        try {
            Optional<DepartmentEntity> department = departmentRepository.findById(departmentId);
            if (department.isPresent()) {
                response.setMessage("Department information retrieved successfully");
                response.setStatusCode(200);
                response.setDepartmentDto(DtoConverter.convertDepartmentEntityToDepartmentDto(department.get()));
            } else {
                response.setMessage("We couldn't find the department you're looking for");
                response.setStatusCode(404);
            }
        } catch (Exception e) {
            response.setMessage("We're having trouble retrieving department information. Please try again.");
            response.setStatusCode(500);
            // Log the actual error for debugging: log.error("Error fetching department", e);
        }
        return response;
    }


    @Override
    public Response getAllDepartments() {
        Response response = new Response();
        try {
            List<DepartmentEntity> departmentEntityList = departmentRepository.findAll();
            if (departmentEntityList.isEmpty()) {
                response.setMessage("No departments found");
                response.setStatusCode(404);
            } else {
                response.setMessage("Departments retrieved successfully");
                response.setStatusCode(200);
                response.setDepartmentDtos(DtoConverter.convertDepartmentEntityListToDepartmentDtoList(departmentEntityList));
            }
        } catch (Exception e) {
            response.setMessage("We're having trouble retrieving the department list. Please try again.");
            response.setStatusCode(500);
            // Log the actual error for debugging: log.error("Error fetching all departments", e);
        }
        return response;
    }

    @Override
    public Response getAllDoctorsByDepartment(Long departmentId) {
        Response response = new Response();
        try {
            // First check if department exists
            Optional<DepartmentEntity> department = departmentRepository.findById(departmentId);

            if (!department.isPresent()) {
                response.setMessage("We couldn't find the department you're looking for");
                response.setStatusCode(404);
                return response;
            }

            // Then get doctors by department
            List<DoctorEntity> doctors = doctorRepository.findByDepartmentId(departmentId);

            if (doctors.isEmpty()) {
                response.setMessage("No doctors found in this department");
                response.setStatusCode(200);
            } else {
                response.setMessage("Doctors retrieved successfully");
                response.setStatusCode(200);

                // Convert both department and doctors to DTOs
                DepartmentDto departmentDto = DtoConverter.convertDepartmentEntityToDepartmentDto(department.get());
                List<DoctorDto> doctorDtos = DtoConverter.convertDoctorEntityListToDoctorDtoList(department.get().getDoctors());

                departmentDto.setDoctors(doctorDtos);
                response.setDepartmentDto(departmentDto);
            }
        } catch (Exception e) {
            response.setMessage("We're having trouble retrieving the doctor list. Please try again.");
            response.setStatusCode(500);

        }
        return response;
    }
}
