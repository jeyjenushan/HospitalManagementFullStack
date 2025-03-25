package org.ai.hospitalmanagementapplicationbackend.Service;

import org.ai.hospitalmanagementapplicationbackend.Dto.Response;
import org.ai.hospitalmanagementapplicationbackend.Entity.UserEntity;

public interface ForgotPasswordHandlerService {
     Response sendForgetPasswordOtp(String email);
    Response verifyOtp(String email, String otp);
    Response resetPassword(String email, String newPassword, String otp);
    void updatePassword(UserEntity userAccount, String newPassword);
    boolean OtpCheck(String email, String otp);
}
