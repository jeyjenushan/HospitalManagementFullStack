package org.ai.hospitalmanagementapplicationbackend.service;

import lombok.AllArgsConstructor;
import org.ai.hospitalmanagementapplicationbackend.dto.AppointmentDto;
import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.AppointmentEntity;
import org.ai.hospitalmanagementapplicationbackend.enumpack.AppointmentStatus;
import org.ai.hospitalmanagementapplicationbackend.mapper.DtoConverter;
import org.ai.hospitalmanagementapplicationbackend.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@AllArgsConstructor
public class AppointmentServiceImplementation implements AppointmentService{
    private final AppointmentRepository appointmentRepository;
    @Override
    public Response bookAppointment(AppointmentEntity appointment) {
        Response response = new Response();

        try {
            // Validate required fields
            if (appointment.getPatient() == null) {
                response.setStatusCode(400);
                response.setMessage("Please select a patient for the appointment");
                return response;
            }
            if (appointment.getDoctor() == null) {
                response.setStatusCode(400);
                response.setMessage("Please select a doctor for the appointment");
                return response;
            }
            if (appointment.getAppointmentTime() == null) {
                response.setStatusCode(400);
                response.setMessage("Please select an appointment time");
                return response;
            }

            // Create and save the appointment
            AppointmentEntity appointmentEntity = new AppointmentEntity();
            appointmentEntity.setPatient(appointment.getPatient());
            appointmentEntity.setDoctor(appointment.getDoctor());
            appointmentEntity.setAppointmentTime(appointment.getAppointmentTime());
            appointmentEntity.setAppointmentStatus(AppointmentStatus.SCHEDULED);

            AppointmentEntity savedAppointment = appointmentRepository.save(appointmentEntity);
            AppointmentDto appointmentDto = DtoConverter.convertAppointmentEntityToAppointmentDto(savedAppointment);

            response.setStatusCode(200);
            response.setMessage("Your appointment has been successfully booked for " +
                    (appointment.getAppointmentTime()));
            response.setAppointmentDto(appointmentDto);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            //response.setMessage("We couldn't process your appointment booking. Please try again later.");

        }
        return response;
    }


    @Override
    public Response cancelAppointment(Long appointmentId) {
        Response response = new Response();

        try {
            Optional<AppointmentEntity> optionalAppointment = appointmentRepository.findById(appointmentId);

            if (optionalAppointment.isPresent()) {
                AppointmentEntity appointment = optionalAppointment.get();

                // Check if already cancelled
                if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
                    response.setStatusCode(400);
                    response.setMessage("This appointment was already cancelled");
                    return response;
                }

                appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
                appointmentRepository.save(appointment);

                response.setStatusCode(200);
                response.setMessage("Your appointment has been successfully cancelled");
                response.setAppointmentDto(DtoConverter.convertAppointmentEntityToAppointmentDto(appointment));
            } else {
                response.setStatusCode(404);
                response.setMessage("We couldn't find the appointment you're trying to cancel");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("We couldn't process your cancellation request. Please try again later.");
        }
        return response;
    }




    @Override
    public Response rescheduleAppointment(AppointmentEntity appointment, Long appointmentId) {
        Response response = new Response();

        try {
            if (appointment.getAppointmentTime() == null) {
                response.setStatusCode(400);
                response.setMessage("Please select a new time for your appointment");
                return response;
            }

            Optional<AppointmentEntity> optionalAppointment = appointmentRepository.findById(appointmentId);

            if (optionalAppointment.isPresent()) {
                AppointmentEntity existingAppointment = optionalAppointment.get();

                // Check if trying to reschedule a cancelled appointment
                if (existingAppointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
                    response.setStatusCode(400);
                    response.setMessage("Cannot reschedule a cancelled appointment. Please book a new one.");
                    return response;
                }

                existingAppointment.setAppointmentTime(appointment.getAppointmentTime());
                AppointmentEntity updatedAppointment = appointmentRepository.save(existingAppointment);
                AppointmentDto appointmentDto = DtoConverter.convertAppointmentEntityToAppointmentDto(updatedAppointment);

                response.setStatusCode(200);
                response.setMessage("Your appointment has been rescheduled to " +
                    (appointment.getAppointmentTime()));
                response.setAppointmentDto(appointmentDto);
            } else {
                response.setStatusCode(404);
                response.setMessage("We couldn't find the appointment you're trying to reschedule");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("We couldn't process your rescheduling request. Please try again later.");
        }
        return response;
    }


    @Override
    public Response getAppointmentsForPatient(Long patientId) {
        Response response = new Response();
        try {
            List<AppointmentEntity> appointments = appointmentRepository.findByPatientId(patientId);

            if (appointments.isEmpty()) {
                response.setStatusCode(200);
                response.setMessage("You don't have any upcoming appointments");
                return response;
            }

            response.setStatusCode(200);
            response.setMessage("Your appointments were retrieved successfully");
            response.setAppointmentDtos(DtoConverter.convertAppointmentEntityListToAppointmentDtoList(appointments));
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("We're having trouble retrieving your appointments. Please try again later.");
        }
        return response;
    }

    @Override
    public Response getAppointmentsForDoctor(Long doctorId) {
        Response response = new Response();
        try {
            List<AppointmentEntity> appointments = appointmentRepository.findByDoctorId(doctorId);

            if (appointments.isEmpty()) {
                response.setStatusCode(200);
                response.setMessage("You don't have any scheduled appointments");
                return response;
            }

            response.setStatusCode(200);
            response.setMessage("Your patient appointments were retrieved successfully");
            response.setAppointmentDtos(DtoConverter.convertAppointmentEntityListToAppointmentDtoList(appointments));
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("We're having trouble retrieving your schedule. Please try again later.");
        }
        return response;
    }

    @Override
    public Response getAppointmentsByStatus(AppointmentStatus status) {
        Response response = new Response();
        try {
            List<AppointmentEntity> appointments = appointmentRepository.findByAppointmentStatus(status);

            if (appointments.isEmpty()) {
                response.setStatusCode(200);
                response.setMessage("No " + status.toString().toLowerCase() + " appointments found");
                return response;
            }

            response.setStatusCode(200);
            response.setMessage(status.toString().toLowerCase() + " appointments retrieved successfully");
            response.setAppointmentDtos(DtoConverter.convertAppointmentEntityListToAppointmentDtoList(appointments));
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("We're having trouble retrieving the appointments. Please try again later.");
        }
        return response;
    }



}
