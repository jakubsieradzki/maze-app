package org.challenge.maze.infrastructure.http.user;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "username cannot be blank")
    public final String username;
    @NotBlank(message = "password cannot be blank")
    public final String password;
}
