package org.ai.hospitalmanagementapplicationbackend.Service;

import org.ai.hospitalmanagementapplicationbackend.Entity.ForgotPasswordToken;
import org.ai.hospitalmanagementapplicationbackend.Entity.UserEntity;


public interface ForgotPasswordService {

    ForgotPasswordToken createToken(UserEntity user, String id, String otp, String sendTo);
    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUser(Long userId) ;
    void deleteToken(ForgotPasswordToken token);
}
