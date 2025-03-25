package org.ai.hospitalmanagementapplicationbackend.service;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.ai.hospitalmanagementapplicationbackend.configuration.JwtTokenProvider;
import org.ai.hospitalmanagementapplicationbackend.dto.Response;
import org.ai.hospitalmanagementapplicationbackend.dto.UserDto;
import org.ai.hospitalmanagementapplicationbackend.entity.*;
import org.ai.hospitalmanagementapplicationbackend.enumpack.Role;
import org.ai.hospitalmanagementapplicationbackend.exception.HospitalException;
import org.ai.hospitalmanagementapplicationbackend.mapper.DtoConverter;
import org.ai.hospitalmanagementapplicationbackend.repository.*;
import org.ai.hospitalmanagementapplicationbackend.request.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final StaffRepository staffRepository;
    private final AdminRepository adminRepository;
    private final DepartmentRepository departmentRepository; // Add this
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Response RegisterPatient(PatientEntity patient) {
        try {
            return registerUser(patient.getUser(), Role.PATIENT, user -> {
                PatientEntity savedPatient = new PatientEntity();
                savedPatient.setUser(user);
                savedPatient.setAge(patient.getAge());
                savedPatient.setGender(patient.getGender());
                savedPatient.setAddress(patient.getAddress());
                savedPatient.setContactNumber(patient.getContactNumber());
                savedPatient.setMedicalHistory(patient.getMedicalHistory());
                savedPatient = patientRepository.save(savedPatient);

                return buildSuccessResponse(savedPatient, jwtTokenProvider.generateToken(user), "Patient");
            });
        } catch (Exception e) {
            return buildErrorResponse("Registration failed: " + e.getMessage(), 500);
        }
    }

    @Override
    public Response RegisterDoctor(DoctorEntity doctor) {
        try {
            String plainTextPassword = doctor.getUser().getPassword();
            return registerUser(doctor.getUser(), Role.DOCTOR, user -> {
                DoctorEntity savedDoctor = new DoctorEntity();

                savedDoctor.setUser(user);
                savedDoctor.setSpecialization(doctor.getSpecialization());
                savedDoctor.setContactNumber(doctor.getContactNumber());
                savedDoctor.setAvailability(doctor.getAvailability());

                if (doctor.getDepartment() != null && doctor.getDepartment().getId() != null) {
                    DepartmentEntity department = departmentRepository.findById(doctor.getDepartment().getId())
                            .orElseThrow(() -> new HospitalException("Department not found"));
                    savedDoctor.setDepartment(department);
                }

                savedDoctor = doctorRepository.save(savedDoctor);

                try {
                    sendRegistrationEmails(user, "Doctor", plainTextPassword);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                return buildSuccessResponse(savedDoctor, jwtTokenProvider.generateToken(user), "Doctor");
            });
        } catch (Exception e) {
            return buildErrorResponse("Registration failed: " + e.getMessage(), 500);
        }
    }

    @Override
    public Response RegisterStaff(StaffEntity staff) {
        try {
            String plainTextPassword = staff.getUser().getPassword();
            return registerUser(staff.getUser(), Role.STAFF, user -> {
                StaffEntity savedStaff = new StaffEntity();
                UserEntity managedUser = userRepository.findById(user.getId())
                        .orElseThrow(() -> new HospitalException("User not found"));

                savedStaff.setUser(managedUser);
                savedStaff.setContactNumber(staff.getContactNumber());
                savedStaff.setStaffrole(staff.getStaffrole());

                if (staff.getDepartment() != null && staff.getDepartment().getId() != null) {
                    Optional<DepartmentEntity> department = departmentRepository.findById(staff.getDepartment().getId());

                    savedStaff.setDepartment(department.get());
                }else{
                    savedStaff.setDepartment(null);
                }

                savedStaff = staffRepository.save(savedStaff);

                try {
                    sendRegistrationEmails(user, "Staff", plainTextPassword);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                return buildSuccessResponse(savedStaff, jwtTokenProvider.generateToken(user), "Staff");
            });
        } catch (Exception e) {
            return buildErrorResponse("Registration failed: " + e.getMessage(), 500);
        }
    }

    @Override
    public Response RegisterAdmin(AdminEntity admin) {
        try {
            String password1 = admin.getUser().getPassword();
            return registerUser(admin.getUser(), Role.ADMIN, user -> {
                AdminEntity savedAdmin = new AdminEntity();
                savedAdmin.setUser(user);
                savedAdmin = adminRepository.save(savedAdmin);

                try {
                    sendRegistrationEmails(user, "Admin", password1);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                return buildSuccessResponse(savedAdmin, jwtTokenProvider.generateToken(user), "Admin");
            });
        } catch (Exception e) {
            return buildErrorResponse("Registration failed: " + e.getMessage(), 500);
        }
    }



    private Response registerUser(UserEntity userEntity, Role role, Function<UserEntity, Response> successHandler) {
        try {
            UserEntity existingUser = userRepository.findByEmail(userEntity.getEmail());
            if (existingUser != null) {
                return buildErrorResponse("User already exists with the provided email.", 400);
            }

            UserEntity newUserEntity = saveUserEntity(userEntity, role);
            return successHandler.apply(newUserEntity);

        } catch (Exception e) {
            return buildErrorResponse("Registration failed: " + e.getMessage(), 500);
        }
    }

    private UserEntity saveUserEntity(UserEntity userEntity, Role role) {
        userEntity.setRole(role);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    private void sendRegistrationEmails(UserEntity user, String role, String password) throws MessagingException {
        String userMessage = String.format(
                "Hello %s,\n\n" +
                        "Your registration as a %s is successful.\n" +
                        "Email: %s\n" +
                        "Password: %s\n\n" +
                        "Thank you!",
                user.getName(), role, user.getEmail(), password
        );
        emailService.sendEmail(user.getEmail(), "Registration Successful", userMessage);

        String adminMessage = String.format("Hello Admin,\n\nA new %s has been registered.\nName: %s\nEmail: %s\n\nThank you!",
                role, user.getName(), user.getEmail());
        emailService.sendEmail("admin@example.com", "New " + role + " Registration", adminMessage);
    }

    private Response buildSuccessResponse(Object entity, String token, String role) {
        Response response = new Response();
        response.setStatusCode(200);
        response.setMessage(role + " registered successfully");
        response.setToken(token);

        switch (role) {
            case "Patient":
                response.setPatientDto(DtoConverter.convertPatientEntityToPatientDto((PatientEntity) entity));
                break;
            case "Doctor":
                response.setDoctorDto(DtoConverter.convertDoctorEntityToDoctorDto((DoctorEntity) entity));
                break;
            case "Staff":
                response.setStaffDto(DtoConverter.convertStaffEntityToStaffDto((StaffEntity) entity));
                break;
            case "Admin":
                response.setAdminDto(DtoConverter.convertAdminEntityToAdminDto((AdminEntity) entity));
                break;
        }

        return response;
    }

    private Response buildErrorResponse(String message, int statusCode) {
        Response response = new Response();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        return response;
    }

    @Override
    public Response LoginUser(LoginRequest loginRequest) {
        Response response = new Response();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword()
                    )
            );
            UserEntity userEntity = userRepository.findByEmail(loginRequest.getEmail());
            String token = jwtTokenProvider.generateToken(userEntity);
            Date expirationDate = jwtTokenProvider.extractExpiration(token);
            UserDto userDto = DtoConverter.convertUserEntityToUserDto(userEntity);

            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(userDto.getRole());
            response.setExpirationTime(String.valueOf(expirationDate));
            response.setUserDto(userDto);
            response.setMessage("The account has been logged in successfully.");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Login failed: " + e.getMessage());
        }
        return response;
    }

}