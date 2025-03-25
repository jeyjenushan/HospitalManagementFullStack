package org.ai.hospitalmanagementapplicationbackend.service;

import lombok.AllArgsConstructor;
import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.entity.ForgotPasswordToken;
import org.ai.hospitalmanagementapplicationbackend.entity.UserEntity;
import org.ai.hospitalmanagementapplicationbackend.otpUtils.GenerateOtp;
import org.ai.hospitalmanagementapplicationbackend.repository.ForgotPasswordRepository;
import org.ai.hospitalmanagementapplicationbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ForgotPasswordHandlerImplementation implements ForgotPasswordHandlerService {

private final UserRepository userRepository;
private final ForgotPasswordRepository forgotPasswordRepository;
private final ForgotPasswordService forgotPasswordService;
private final EmailService emailService;
private final PasswordEncoder passwordEncoder;



    public Response sendForgetPasswordOtp(String email) {
        Response response = new Response();
        try {
            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                response.setStatusCode(404);
                response.setMessage("User not found with provided email.");
                return response;
            }
            String otp = GenerateOtp.generateOtp();
            UUID uuid = UUID.randomUUID();
            String id = uuid.toString();
            ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());
            if (token == null) {
                token = forgotPasswordService.createToken(user, id, otp, email);
            }
            emailService.sendEmail(email,"Your Forgot Password Verification Code\n\n","Your verification code is " + token.getOtp());


            response.setStatusCode(200);
            response.setMessage("Password reset OTP sent successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error sending password reset OTP: " + e.getMessage());
        }
        return response;
    }

    public Response verifyOtp(String email, String otp) {
        Response response = new Response();
        try {
            UserEntity userAccount = userRepository.findByEmail(email);
            if (userAccount == null) {
                response.setStatusCode(404);
                response.setMessage("User not found with provided email.");
                return response;
            }

            ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findByUser(userAccount.getId());
            if (forgotPasswordToken == null) {
                response.setStatusCode(404);
                response.setMessage("Invalid or expired token.");
                return response;
            }

            if (forgotPasswordToken.getOtp().equals(otp)) {
                response.setStatusCode(200);
                response.setMessage("OTP verified successfully.");
            } else {
                response.setStatusCode(400);
                response.setMessage("Wrong OTP provided.");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error verifying OTP: " + e.getMessage());
        }
        return response;
    }

    public Response resetPassword(String email, String newPassword, String otp) {
        Response response = new Response();
        try {
            if (newPassword.length() <= 4) {
                response.setStatusCode(400);
                response.setMessage("Password must be at least 4 characters long.");
                return response;
            }

            boolean isVerified = OtpCheck(email, otp);
            if (isVerified) {
                UserEntity userAccount = userRepository.findByEmail(email);
                if (userAccount == null) {
                    response.setStatusCode(404);
                    response.setMessage("User not found with provided email.");
                    return response;
                }

                updatePassword(userAccount, newPassword);
                response.setStatusCode(200);
                response.setMessage("Password updated successfully!");
            } else {
                response.setStatusCode(400);
                response.setMessage("Wrong OTP provided.");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error resetting password: " + e.getMessage());
        }
        return response;
    }

    public void updatePassword(UserEntity userAccount, String newPassword) {
        try {
            userAccount.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(userAccount);
        } catch (Exception e) {
            throw new RuntimeException("Error updating password: " + e.getMessage());
        }
    }

    public boolean OtpCheck(String email, String otp) {
        Response response = new Response();
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            response.setStatusCode(404);
            response.setMessage("User not found with provided email.");
        }
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findByUser(user.getId());
        if (forgotPasswordToken == null) {
            response.setStatusCode(404);
            response.setMessage("Invalid or expired token.");
        }
        return forgotPasswordToken.getOtp().equals(otp);
    }
}
