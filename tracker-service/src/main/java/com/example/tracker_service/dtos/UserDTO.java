package com.example.tracker_service.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Schema(description = "Username of the user", example = "user123")
    private String userName;
    @Schema(description = "User email address", example = "user123@example.com")
    private String email;
    @Schema(description = "Roles assigned to the user", example = "ADMIN")
    String  roles;
}
