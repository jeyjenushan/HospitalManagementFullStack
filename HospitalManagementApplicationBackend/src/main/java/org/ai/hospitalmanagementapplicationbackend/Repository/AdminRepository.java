package org.ai.hospitalmanagementapplicationbackend.Repository;

import org.ai.hospitalmanagementapplicationbackend.Entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity,Long> {
}
