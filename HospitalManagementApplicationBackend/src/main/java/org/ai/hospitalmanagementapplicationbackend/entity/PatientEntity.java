package org.ai.hospitalmanagementapplicationbackend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "patients")
@AllArgsConstructor
@NoArgsConstructor
public class PatientEntity {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private int age;
      private String gender;
      private String contactNumber;
      private String address;
      private String medicalHistory;

      @CreationTimestamp
      @Column(updatable = false)
      private LocalDateTime createdAt;

      @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<AppointmentEntity> appointments=new ArrayList<>();

      @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<MedicalRecordEntity> medicalRecords=new ArrayList<>();

      @OneToOne
      @JoinColumn(name = "user_id",referencedColumnName = "id")
      private UserEntity user;



}
