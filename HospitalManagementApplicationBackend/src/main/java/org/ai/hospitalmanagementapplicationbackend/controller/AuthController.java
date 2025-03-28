package org.ai.hospitalmanagementapplicationbackend.controller;

import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.PatientEntity;
import org.ai.hospitalmanagementapplicationbackend.request.LoginRequest;
import org.ai.hospitalmanagementapplicationbackend.service.AuthService;
import org.ai.hospitalmanagementapplicationbackend.service.ForgotPasswordHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private ForgotPasswordHandlerService forgotPasswordHandlerService;

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        Response response=authService.LoginUser(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/auth/patient")
    public ResponseEntity<Response> patientRegister(@RequestBody PatientEntity patient) {
        Response response = authService.RegisterPatient(patient);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/forgotpassword/send-otp")
    public ResponseEntity<Response> sendForgetPasswordOtp(@RequestParam String email){
        Response response=forgotPasswordHandlerService.sendForgetPasswordOtp(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/forgotpassword/verify-otp")
    public ResponseEntity<Response> verifyOtp(@RequestParam String email, @RequestParam String otp)   {
        Response response = forgotPasswordHandlerService.verifyOtp(email,otp);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/forgotpassword/reset-password")
    public ResponseEntity<Response> resetPassword(@RequestParam String email, @RequestParam String newPassword, @RequestParam String otp) {
        Response response = forgotPasswordHandlerService.resetPassword(email,newPassword,otp);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



}
