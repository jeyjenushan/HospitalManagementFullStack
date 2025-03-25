package org.ai.hospitalmanagementapplicationbackend.Service;

import org.ai.hospitalmanagementapplicationbackend.Dto.Response;
import org.ai.hospitalmanagementapplicationbackend.Entity.*;
import org.ai.hospitalmanagementapplicationbackend.Request.LoginRequest;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {

    Response RegisterPatient( PatientEntity patient);
    Response LoginUser( LoginRequest loginRequest);
    Response RegisterDoctor( DoctorEntity doctor);
    Response RegisterStaff( StaffEntity staff);
    Response RegisterAdmin( AdminEntity admin);

}
