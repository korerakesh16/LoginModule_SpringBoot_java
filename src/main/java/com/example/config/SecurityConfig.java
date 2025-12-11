package com.example.springbootlogin.config;

import com.example.springbootlogin.service.CustomUserDetailsService;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService uds) {
        this.userDetailsService = uds;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider prov = new DaoAuthenticationProvider();
        prov.setUserDetailsService(userDetailsService);
        prov.setPasswordEncoder(passwordEncoder());
        return prov;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/register", "/css/**", "/js/**", "/images/**").permitAll()
              .anyRequest().authenticated()
          )
          .formLogin(form -> form
              .loginPage("/login")
              .defaultSuccessUrl("/home", true)
              .permitAll()
          )
          .logout(logout -> logout
              .logoutUrl("/logout")
              .logoutSuccessUrl("/login?logout")
              .permitAll()
          );

        // CSRF enabled by default for forms
        return http.build();
    }
}
