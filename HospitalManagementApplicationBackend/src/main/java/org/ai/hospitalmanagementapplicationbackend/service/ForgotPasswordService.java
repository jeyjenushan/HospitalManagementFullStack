package org.ai.hospitalmanagementapplicationbackend.service;

import org.ai.hospitalmanagementapplicationbackend.entity.ForgotPasswordToken;
import org.ai.hospitalmanagementapplicationbackend.entity.UserEntity;


public interface ForgotPasswordService {

    ForgotPasswordToken createToken(UserEntity user, String id, String otp, String sendTo);
    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUser(Long userId) ;
    void deleteToken(ForgotPasswordToken token);
}
