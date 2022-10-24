package org.challenge.maze.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.challenge.maze.domain.exception.UserAlreadyExistsException;
import org.challenge.maze.infrastructure.db.UserRepository;
import org.challenge.maze.infrastructure.db.entities.UserEntity;
import org.challenge.maze.infrastructure.http.user.CreateUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void createNewUser(CreateUserRequest createUserRequest) {
        log.info("Crating new user: {}", createUserRequest.username);
        boolean userExists = userRepository.existsById(createUserRequest.username);
        if (userExists) {
            throw new UserAlreadyExistsException(createUserRequest.username);
        } else {
            userRepository.save(toUser(createUserRequest));
        }
    }

    private UserEntity toUser(CreateUserRequest createUserRequest) {
        return new UserEntity()
                .setUsername(createUserRequest.username)
                .setPassword(passwordEncoder.encode(createUserRequest.password));
    }
}
