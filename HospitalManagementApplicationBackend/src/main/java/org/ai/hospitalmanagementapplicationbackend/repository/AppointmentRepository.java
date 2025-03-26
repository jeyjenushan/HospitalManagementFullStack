package org.ai.hospitalmanagementapplicationbackend.repository;

import org.ai.hospitalmanagementapplicationbackend.entity.AppointmentEntity;
import org.ai.hospitalmanagementapplicationbackend.enumpack.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long> {

    List<AppointmentEntity> findByPatientId(Long patientId);
    List<AppointmentEntity> findByDoctorId(Long doctorId);
    List<AppointmentEntity> findByAppointmentStatus(AppointmentStatus appointmentStatus);

}
