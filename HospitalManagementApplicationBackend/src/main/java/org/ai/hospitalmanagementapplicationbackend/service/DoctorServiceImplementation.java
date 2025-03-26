package org.ai.hospitalmanagementapplicationbackend.service;

import lombok.AllArgsConstructor;
import org.ai.hospitalmanagementapplicationbackend.dto.DoctorDto;
import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.DepartmentEntity;
import org.ai.hospitalmanagementapplicationbackend.entity.DoctorEntity;
import org.ai.hospitalmanagementapplicationbackend.entity.PatientEntity;
import org.ai.hospitalmanagementapplicationbackend.mapper.DtoConverter;
import org.ai.hospitalmanagementapplicationbackend.repository.DepartmentRepository;
import org.ai.hospitalmanagementapplicationbackend.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorServiceImplementation implements DoctorService{

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;


    @Override
    public Response fetchAllDoctors() {
        Response response = new Response();
        try {
            List<DoctorEntity> doctors = doctorRepository.findAll();
            if (doctors.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No doctors found in the system");
            } else {
                response.setDoctorDtos(DtoConverter.convertDoctorEntityListToDoctorDtoList(doctors));
                response.setStatusCode(200);
                response.setMessage("Doctors retrieved successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Failed to retrieve doctors. Please try again later.");
        }
        return response;
    }

    @Override
    public Response fetchDoctorById(Long id) {
        Response response=new Response();
        try {
            if (id == null || id <= 0) {
                response.setStatusCode(400);
                response.setMessage("Invalid patient ID provided");
                return response;
            }
            Optional<DoctorEntity> doctor=doctorRepository.findById(id);
            if(doctor.isPresent()) {
                response.setDoctorDto(DtoConverter.convertDoctorEntityToDoctorDto(doctor.get()));
                response.setStatusCode(200);
                response.setMessage("Doctor details retrieved successfully");
            }else{
                response.setStatusCode(500);
                response.setMessage("Doctor with ID " + id + " not found");
            }

        }catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving Doctor information. Please try again.");
        }
        return response;
    }

    @Override
    public Response updateDoctor(Long doctorId, DoctorEntity updatedDoctor) {
        Response response = new Response();

        try {
            // Validate doctorId
            if (doctorId == null || doctorId <= 0) {
                response.setStatusCode(400);
                response.setMessage("Invalid doctor ID");
                return response;
            }

            // Check if updatedDoctor is null
            if (updatedDoctor == null) {
                response.setStatusCode(400);
                response.setMessage("Updated doctor data cannot be null");
                return response;
            }

            // Find existing doctor
            DoctorEntity existingDoctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            // Update user name if provided
            if (updatedDoctor.getUser() != null) {
                if (updatedDoctor.getUser().getName() != null && !updatedDoctor.getUser().getName().trim().isEmpty()) {
                    existingDoctor.getUser().setName(updatedDoctor.getUser().getName().trim());
                }
                // Update user ID if provided
                if (updatedDoctor.getUser().getId() != null) {
                    existingDoctor.getUser().setId(updatedDoctor.getUser().getId());
                }
            }

            // Update specialization if provided
            if (updatedDoctor.getSpecialization() != null && !updatedDoctor.getSpecialization().trim().isEmpty()) {
                existingDoctor.setSpecialization(updatedDoctor.getSpecialization().trim());
            }

            // Update availability if provided
            if (updatedDoctor.getAvailability() != null) {
                existingDoctor.setAvailability(updatedDoctor.getAvailability());
            }

            // Update contact number if provided
            if (updatedDoctor.getContactNumber() != null && !updatedDoctor.getContactNumber().trim().isEmpty()) {
                existingDoctor.setContactNumber(updatedDoctor.getContactNumber().trim());
            }

            // Update department if provided
            if (updatedDoctor.getDepartment() != null && updatedDoctor.getDepartment().getId() != null) {
                departmentRepository.findById(updatedDoctor.getDepartment().getId())
                        .orElseThrow(() -> new RuntimeException("Department not found"));
                existingDoctor.setDepartment(updatedDoctor.getDepartment());
            }

            // Update doctor ID if provided
            if (updatedDoctor.getId() != null) {
                existingDoctor.setId(updatedDoctor.getId());
            }

            // Save the updated doctor
            DoctorEntity savedDoctor = doctorRepository.save(existingDoctor);

            response.setStatusCode(200);
            response.setMessage("Successfully updated doctor with id " + doctorId);
            response.setDoctorDto(DtoConverter.convertDoctorEntityToDoctorDto(savedDoctor));

        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Failed to update doctor details. Please try again later.");
        }

        return response;
    }

    @Override
    public Response deleteDoctor(Long id) {
        Response response = new Response();
        try {
            if (id == null || id <= 0) {
                response.setStatusCode(400);
                response.setMessage("Invalid doctor ID");
                return response;
            }

            if (!doctorRepository.existsById(id)) {
                response.setStatusCode(404);
                response.setMessage("Doctor not found with id: " + id);
                return response;
            }

            doctorRepository.deleteById(id);
            response.setStatusCode(200);
            response.setMessage("Doctor deleted successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Failed to delete doctor. Please try again later.");
        }
        return response;
    }

    @Override
    public Response getDoctorsBySpecialization(String specialization) {
        Response response = new Response();
        try {
            if (specialization == null || specialization.trim().isEmpty()) {
                response.setStatusCode(400);
                response.setMessage("Specialization parameter is required");
                return response;
            }

            List<DoctorEntity> doctors = doctorRepository.findBySpecializationContainingIgnoreCase(specialization.trim());
            if (doctors.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No doctors found with specialization: " + specialization);
            } else {
                response.setDoctorDtos(DtoConverter.convertDoctorEntityListToDoctorDtoList(doctors));
                response.setStatusCode(200);
                response.setMessage("Doctors retrieved successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Failed to retrieve doctors by specialization. Please try again later.");
        }
        return response;
    }

    @Override
    public Response getDoctorsByDepartment(Long departmentId) {
        Response response = new Response();
        try {
            if (departmentId == null || departmentId <= 0) {
                response.setStatusCode(400);
                response.setMessage("Valid department ID is required");
                return response;
            }

            if (!departmentRepository.existsById(departmentId)) {
                response.setStatusCode(404);
                response.setMessage("Department not found with id: " + departmentId);
                return response;
            }

            List<DoctorEntity> doctors = doctorRepository.findByDepartmentId(departmentId);
            if (doctors.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No doctors found in department with id: " + departmentId);
            } else {
                response.setDoctorDtos(DtoConverter.convertDoctorEntityListToDoctorDtoList(doctors));
                response.setStatusCode(200);
                response.setMessage("Doctors retrieved successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Failed to retrieve doctors by department. Please try again later.");
        }
        return response;
    }

    @Override
    public Response checkDoctorAvailability(Long id) {
        Response response = new Response();
        try {
            if (id == null || id <= 0) {
                response.setStatusCode(400);
                response.setMessage("Invalid doctor ID");
                return response;
            }

            DoctorEntity doctor = doctorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

            if (doctor.getAvailability() == null) {
                response.setStatusCode(404);
                response.setMessage("Availability information not available for this doctor");
            } else {
                DoctorDto doctorDto =DtoConverter.convertDoctorEntityToDoctorDto(doctor);
                doctorDto.setAvailability(doctor.getAvailability());
                response.setStatusCode(200);
                response.setMessage("Doctor availability retrieved successfully");
                response.setDoctorDto(doctorDto);
            }
        } catch (RuntimeException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Failed to check doctor availability. Please try again later.");
        }
        return response;
    }


}
