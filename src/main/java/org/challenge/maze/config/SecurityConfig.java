package org.challenge.maze.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.util.concurrent.ConcurrentHashMap;

@EnableSpringHttpSession
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain usersFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/users")
                .antMatcher("/login")
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/users").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .failureHandler(loginAuthenticationFailureHandler())
                        .successHandler(successfulLoginHandler())
                );

        return http.build();
    }

    private AuthenticationFailureHandler loginAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    private SuccessfulLoginHandler successfulLoginHandler() {
        return new SuccessfulLoginHandler();
    }

    @Bean
    public SecurityFilterChain mazeManagementFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/maze/**")
                .authorizeRequests(auth -> auth.anyRequest().authenticated())
                // do not create session for unauthenticated requests
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .requestCache().requestCache(new NullRequestCache());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    public HttpSessionIdResolver headerSessionResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

    @Bean
    public MapSessionRepository sessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
