package org.challenge.maze.infrastructure.http.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenge.maze.application.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@AllArgsConstructor
public class UserApi {

    private final UserService userService;

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        userService.createNewUser(createUserRequest);
        log.info("Successfully created user: {}", createUserRequest.username);
        return ResponseEntity.noContent()
                .build();
    }
}
