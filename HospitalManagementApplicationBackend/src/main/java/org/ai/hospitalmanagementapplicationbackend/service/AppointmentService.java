package org.ai.hospitalmanagementapplicationbackend.service;

import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.AppointmentEntity;
import org.ai.hospitalmanagementapplicationbackend.enumpack.AppointmentStatus;

public interface AppointmentService {

    Response bookAppointment(AppointmentEntity appointment);
    Response cancelAppointment(Long appointmentId);
    Response rescheduleAppointment(AppointmentEntity appointment,Long appointmentId);
    Response getAppointmentsForPatient(Long patientId);
    Response getAppointmentsForDoctor(Long doctorId);
    Response getAppointmentsByStatus(AppointmentStatus status);

}
