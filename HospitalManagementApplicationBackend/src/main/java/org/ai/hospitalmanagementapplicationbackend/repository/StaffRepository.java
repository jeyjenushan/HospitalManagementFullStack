package org.ai.hospitalmanagementapplicationbackend.repository;

import org.ai.hospitalmanagementapplicationbackend.entity.DepartmentEntity;
import org.ai.hospitalmanagementapplicationbackend.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity,Long> {
    List<StaffEntity> findByDepartment(DepartmentEntity departmentEntity);

    List<StaffEntity> findByStaffrole(String role);
}
