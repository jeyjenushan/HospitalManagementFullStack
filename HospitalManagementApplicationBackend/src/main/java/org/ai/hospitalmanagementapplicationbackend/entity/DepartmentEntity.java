package org.ai.hospitalmanagementapplicationbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "departments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "department",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<DoctorEntity> doctors;

    @OneToMany(mappedBy = "department",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<StaffEntity> staffEntities;


}
