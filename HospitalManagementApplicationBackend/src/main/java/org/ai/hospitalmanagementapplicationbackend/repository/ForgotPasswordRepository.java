package org.ai.hospitalmanagementapplicationbackend.repository;

import org.ai.hospitalmanagementapplicationbackend.entity.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,String> {
    ForgotPasswordToken findByUserEntity_id(Long userId);
}