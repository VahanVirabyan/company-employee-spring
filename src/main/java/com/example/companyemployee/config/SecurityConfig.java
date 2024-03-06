package com.example.companyemployee.config;

import com.example.companyemployee.entity.UserRole;
import jakarta.security.auth.message.config.AuthConfigProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/user/register").permitAll()
                .requestMatchers("/loginPage").permitAll()
                .requestMatchers("/add/company").hasAnyAuthority(UserRole.ADMIN.name())
                .requestMatchers("/companies").hasAnyAuthority(UserRole.ADMIN.name(), UserRole.USER.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/login")
//                .successForwardUrl("/loginSuccess")
                .defaultSuccessUrl("/loginSuccess", true)
                .and()
                .logout()
                .logoutSuccessUrl("/");
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authConfigProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
}