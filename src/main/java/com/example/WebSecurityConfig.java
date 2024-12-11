package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        
        return authProvider;
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        
        http
            .authorizeRequests(auth ->
                auth
                    .requestMatchers("/fitness/**").authenticated() // Protect fitness pages with authentication
                    .requestMatchers("/users").authenticated() // Protect users page with authentication
                    .anyRequest().permitAll() // Allow any other request
            )
            .formLogin(login ->
                login
                    .usernameParameter("email") // Define the username parameter as email
                    .defaultSuccessUrl("/fitness", true) // Redirect to /fitness after login
                    .permitAll()
            )
            .logout(logout ->
                logout
                    .logoutSuccessUrl("/") // Redirect to home page after logout
                    .permitAll()
            );

        return http.build();
    }
}
