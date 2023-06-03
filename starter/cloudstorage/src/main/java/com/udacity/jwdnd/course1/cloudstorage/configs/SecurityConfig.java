package com.udacity.jwdnd.course1.cloudstorage.configs;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(request -> request.requestMatchers("/signup", "signup/success",  "/css/**", "/js/**")
                        .permitAll().anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").failureUrl("/login/error").permitAll()
                        .defaultSuccessUrl("/home", true))
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }
}
