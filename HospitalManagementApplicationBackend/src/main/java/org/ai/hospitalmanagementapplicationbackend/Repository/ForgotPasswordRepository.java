package org.ai.hospitalmanagementapplicationbackend.Repository;

import org.ai.hospitalmanagementapplicationbackend.Entity.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,String> {
    ForgotPasswordToken findByUserEntity_id(Long userId);
}