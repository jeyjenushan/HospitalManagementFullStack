package org.ai.hospitalmanagementapplicationbackend.mapper;

import org.ai.hospitalmanagementapplicationbackend.dto.*;
import org.ai.hospitalmanagementapplicationbackend.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public  class  DtoConverter {


    public static UserDto convertUserEntityToUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setRole(userEntity.getRole());
        userDto.setName(userEntity.getName());

        return userDto;
    }

    public static List<UserDto> convertUserEntityListToUserDtoList(List<UserEntity> UserEntityList) {
        return UserEntityList.stream().map(DtoConverter::convertUserEntityToUserDto).collect(Collectors.toList());
    }

    public static PatientDto convertPatientEntityToPatientDto(PatientEntity patientEntity) {

        PatientDto patientDto = new PatientDto();
        patientDto.setId(patientEntity.getId());
        patientDto.setAge(patientEntity.getAge());
        patientDto.setGender(patientEntity.getGender());
        if (patientEntity.getUser() != null) {
            UserEntity userEntity = patientEntity.getUser();
            UserDto userDto = convertUserEntityToUserDto(userEntity);
            patientDto.setUser(userDto);
        }
        patientDto.setContactNumber(String.valueOf(patientEntity.getContactNumber()));
        patientDto.setMedicalHistory(patientEntity.getMedicalHistory());
        patientDto.setCreatedAt(patientEntity.getCreatedAt());

        return patientDto;



    }

    public static List<PatientDto> convertPatientEntityListToPatientDtoList(List<PatientEntity> patientEntityList) {
        return patientEntityList.stream().map(DtoConverter::convertPatientEntityToPatientDto).collect(Collectors.toList());
    }



    public static DoctorDto convertDoctorEntityToDoctorDto(DoctorEntity savedDoctor) {
        if (savedDoctor == null) {
            return null;
        }

        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setId(savedDoctor.getId());
        doctorDto.setSpecialization(savedDoctor.getSpecialization());
        doctorDto.setContactNumber(savedDoctor.getContactNumber());
        doctorDto.setAvailability(savedDoctor.getAvailability());

        if (savedDoctor.getUser() != null) {
       UserEntity userEntity = savedDoctor.getUser();
       UserDto userDto = convertUserEntityToUserDto(userEntity);
       doctorDto.setUser(userDto);
        }

        if (savedDoctor.getDepartment() != null) {
           DepartmentEntity departmentEntity = savedDoctor.getDepartment();
           DepartmentDto departmentDto=convertDepartmentEntityToDepartmentDto(departmentEntity);
           doctorDto.setDepartment(departmentDto);
        }

        return doctorDto;
    }

    public static List<DoctorDto> convertDoctorEntityListToDoctorDtoList(List<DoctorEntity> doctorEntityList) {
        return doctorEntityList.stream().map(DtoConverter::convertDoctorEntityToDoctorDto).collect(Collectors.toList());
    }



    public static DepartmentDto convertDepartmentEntityToDepartmentDto(DepartmentEntity departmentEntity) {
        DepartmentDto departmentDto = new DepartmentDto();
       departmentDto.setDescription(departmentEntity.getDescription());
       departmentDto.setId(departmentEntity.getId());
       departmentDto.setName(departmentEntity.getName());

        return departmentDto;
    }
    public static List<DepartmentDto> convertDepartmentEntityListToDepartmentDtoList(List<DepartmentEntity> departmentEntityList) {
        return departmentEntityList.stream().map(DtoConverter::convertDepartmentEntityToDepartmentDto).collect(Collectors.toList());
    }

    public static StaffDto convertStaffEntityToStaffDto(StaffEntity savedStaff) {
        if (savedStaff == null) {
            return null;
        }

        StaffDto staffDto = new StaffDto();
         staffDto.setId(savedStaff.getId());
         staffDto.setRole(savedStaff.getStaffrole());
         staffDto.setDepartmentId(savedStaff.getDepartment().getId());
         staffDto.setContactNumber(savedStaff.getContactNumber());


        if (savedStaff.getUser() != null) {
            UserEntity userEntity = savedStaff.getUser();
            UserDto userDto = convertUserEntityToUserDto(userEntity);
            staffDto.setUserDto(userDto);
        }



        return staffDto;


    }
    public static List<StaffDto> convertStaffEntityListToStaffDtoList(List<StaffEntity> staffEntityList) {
        return staffEntityList.stream().map(DtoConverter::convertStaffEntityToStaffDto).collect(Collectors.toList());
    }

    public static AdminDto convertAdminEntityToAdminDto(AdminEntity savedAdmin) {
        if (savedAdmin == null) {
            return null;
        }

        AdminDto adminDto = new AdminDto();
        adminDto.setId(savedAdmin.getId());
        if (savedAdmin.getUser() != null) {
            UserEntity userEntity = savedAdmin.getUser();
            UserDto userDto = convertUserEntityToUserDto(userEntity);
            adminDto.setUser(userDto);
        }
     return adminDto;


    }

    public static List<AdminDto> convertAdminEntityListToAdminDtoList(List<AdminEntity> adminDtoList) {
        return adminDtoList.stream().map(DtoConverter::convertAdminEntityToAdminDto).collect(Collectors.toList());
    }
}
