package org.ai.hospitalmanagementapplicationbackend.Repository;

import org.ai.hospitalmanagementapplicationbackend.Entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long> {
}
