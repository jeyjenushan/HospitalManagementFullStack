package org.ai.hospitalmanagementapplicationbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ai.hospitalmanagementapplicationbackend.enumpack.Role;

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
