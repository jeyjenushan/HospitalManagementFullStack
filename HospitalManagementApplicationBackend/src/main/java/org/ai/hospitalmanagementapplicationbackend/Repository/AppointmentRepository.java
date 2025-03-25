package org.ai.hospitalmanagementapplicationbackend.Repository;

import org.ai.hospitalmanagementapplicationbackend.Entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity,Long> {
}
