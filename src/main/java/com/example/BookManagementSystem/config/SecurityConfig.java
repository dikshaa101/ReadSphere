package com.example.BookManagementSystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(
            @Value("${app.security.admin.username:admin}") String adminUsername,
            @Value("${app.security.admin.password:admin123}") String adminPassword,
            @Value("${app.security.user.username:user}") String userUsername,
            @Value("${app.security.user.password:user123}") String userPassword) {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        UserDetails admin = User.withUsername(adminUsername)
                .passwordEncoder(encoder::encode)
                .password(adminPassword)
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername(userUsername)
                .passwordEncoder(encoder::encode)
                .password(userPassword)
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}
