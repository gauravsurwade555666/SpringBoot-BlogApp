package com.gaurav.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gaurav.blog.security.CustomUserDetailsService;
import com.gaurav.blog.security.JwtAuhtenticationFilter;
import com.gaurav.blog.security.JwtAuthenticationEntryPoint;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(debug=true)
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtAuhtenticationFilter jwtAuhtenticationFilter;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // config for Basic Authentication

        // http
        // .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
        // .authorizeHttpRequests(auth -> auth
        // .requestMatchers("/public/**").permitAll() // Allow public access to
        // /public/**
        // .anyRequest().authenticated() // Require authentication for any other request
        // )
        // .httpBasic(httpBasic -> {}); // Use basic HTTP authentication
        // http.authenticationProvider(this.daoAuthenticationProvider());

        // config for JWT

        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authenticationProvider(this.daoAuthenticationProvider());
        http.addFilterBefore(jwtAuhtenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}