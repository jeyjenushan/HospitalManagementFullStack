package org.ai.hospitalmanagementapplicationbackend.repository;

import org.ai.hospitalmanagementapplicationbackend.entity.DepartmentEntity;
import org.ai.hospitalmanagementapplicationbackend.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity,Long> {


    List<DoctorEntity> findBySpecializationContainingIgnoreCase(String specialization);

    List<DoctorEntity> findByDepartmentId(Long departmentId);
}
