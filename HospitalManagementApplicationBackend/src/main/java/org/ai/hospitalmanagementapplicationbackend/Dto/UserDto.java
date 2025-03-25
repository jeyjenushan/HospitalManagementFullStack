package org.ai.hospitalmanagementapplicationbackend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ai.hospitalmanagementapplicationbackend.Enum.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
private Long id;
private String name;
private String email;
private String password;
private Role role;


}
