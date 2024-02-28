package com.example.Seguridad.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
//    private Set<String>roles;
    private List<RoleIdDTO> roleIds;
    @NotNull
    private Boolean enabled;

    public Boolean isEnabled() {
        return enabled;
    }


}
