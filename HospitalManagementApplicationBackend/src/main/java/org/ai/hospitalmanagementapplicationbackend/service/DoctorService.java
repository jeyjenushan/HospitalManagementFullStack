package org.ai.hospitalmanagementapplicationbackend.service;

import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.DoctorEntity;

public interface DoctorService {

    Response fetchAllDoctors();
    Response fetchDoctorById(Long id);
    Response updateDoctor(Long doctorId, DoctorEntity doctorEntity);
    Response deleteDoctor(Long doctorId);
    Response getDoctorsBySpecialization(String specialization);
    Response getDoctorsByDepartment(Long departmentId);
    Response checkDoctorAvailability(Long doctorId);
}
