package org.challenge.maze.config;

import lombok.AllArgsConstructor;
import org.challenge.maze.infrastructure.db.UserRepository;
import org.challenge.maze.infrastructure.db.entities.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class RepositoryUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such user: " + username));

        return toUserDetails(userEntity);
    }

    private UserDetails toUserDetails(UserEntity userEntity) {
        return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
    }
}
