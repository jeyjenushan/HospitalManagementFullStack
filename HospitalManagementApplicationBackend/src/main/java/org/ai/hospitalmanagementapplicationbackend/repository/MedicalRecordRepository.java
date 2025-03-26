package org.ai.hospitalmanagementapplicationbackend.repository;

import org.ai.hospitalmanagementapplicationbackend.entity.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecordEntity,Long> {
    List<MedicalRecordEntity> findByPatientId(Long patientId);
}
