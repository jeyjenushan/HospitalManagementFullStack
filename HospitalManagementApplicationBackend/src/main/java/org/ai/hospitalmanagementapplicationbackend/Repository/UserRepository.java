package org.ai.hospitalmanagementapplicationbackend.Repository;

import org.ai.hospitalmanagementapplicationbackend.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByEmail(String email);

}
