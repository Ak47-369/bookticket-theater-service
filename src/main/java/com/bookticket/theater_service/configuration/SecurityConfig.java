package com.bookticket.theater_service.configuration;


import com.bookticket.theater_service.security.HeaderAuthenticatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public HeaderAuthenticatorFilter headerAuthenticatorFilter() {
        return new HeaderAuthenticatorFilter();
    }

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/shows/internal/**").hasRole("SERVICE_ACCOUNT")
                        .requestMatchers(HttpMethod.POST,"/api/v1/shows/**").hasAnyRole("ADMIN","THEATER_OWNER")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/shows/**").hasAnyRole("ADMIN", "THEATER_OWNER")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/shows/**").hasAnyRole("ADMIN", "THEATER_OWNER")
                        .requestMatchers(HttpMethod.POST,"/api/v1/theaters/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/theaters/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/theaters/**").hasAnyRole("ADMIN", "THEATER_OWNER")
                        .requestMatchers(HttpMethod.GET,"/api/v1/theaters/**").hasAnyRole("ADMIN", "USER","THEATER_OWNER")
                        .requestMatchers(HttpMethod.GET,"/api/v1/theaters/screens/**").hasAnyRole("ADMIN", "USER","THEATER_OWNER")
                        .requestMatchers(HttpMethod.POST,"/api/v1/theaters/screens/**").hasAnyRole("ADMIN", "THEATER_OWNER")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/theaters/screens/**").hasAnyRole("ADMIN", "THEATER_OWNER")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/theaters/screens/**").hasAnyRole("ADMIN", "THEATER_OWNER")
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/shows/**").permitAll()
                        .anyRequest().authenticated()
                );
        http.addFilterBefore(headerAuthenticatorFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
