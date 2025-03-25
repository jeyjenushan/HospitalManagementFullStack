package org.ai.hospitalmanagementapplicationbackend.service;

import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.*;
import org.ai.hospitalmanagementapplicationbackend.request.LoginRequest;

public interface AuthService {

    Response RegisterPatient( PatientEntity patient);
    Response LoginUser( LoginRequest loginRequest);
    Response RegisterDoctor( DoctorEntity doctor);
    Response RegisterStaff( StaffEntity staff);
    Response RegisterAdmin( AdminEntity admin);

}
