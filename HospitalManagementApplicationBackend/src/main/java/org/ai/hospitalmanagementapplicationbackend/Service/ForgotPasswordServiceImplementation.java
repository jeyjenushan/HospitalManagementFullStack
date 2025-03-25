package org.ai.hospitalmanagementapplicationbackend.Service;

import org.ai.hospitalmanagementapplicationbackend.Entity.ForgotPasswordToken;
import org.ai.hospitalmanagementapplicationbackend.Entity.UserEntity;
import org.ai.hospitalmanagementapplicationbackend.Repository.ForgotPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ForgotPasswordServiceImplementation implements ForgotPasswordService{
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;



    @Override
    public ForgotPasswordToken createToken(UserEntity user, String id, String otp, String sendTo) {
        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setUserEntity(user);
        forgotPasswordToken.setOtp(otp);
        forgotPasswordToken.setId(id);
        forgotPasswordToken.setEmail(sendTo);
        return forgotPasswordRepository.save(forgotPasswordToken);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
       Optional<ForgotPasswordToken> forgotPasswordToken = forgotPasswordRepository.findById(id);
       return forgotPasswordToken.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long userId) {
        return forgotPasswordRepository.findByUserEntity_id(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
        forgotPasswordRepository.delete(token);

    }
}
