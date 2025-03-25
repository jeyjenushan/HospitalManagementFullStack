package org.ai.hospitalmanagementapplicationbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForgotPasswordToken {
    @Id
    private String id;
    @OneToOne
    private UserEntity userEntity;
    private String otp;
    private String email;
}
