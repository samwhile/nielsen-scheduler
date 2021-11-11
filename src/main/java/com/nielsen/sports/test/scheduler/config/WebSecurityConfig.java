package com.nielsen.sports.test.scheduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * In a production setting this API could be secured using oauth2 or JWT.
 * I have omitted code up for sake of brevity and use of testing locally.
 */
@Configuration
public class WebSecurityConfig {

    /**
     * not used
     */
    private AuthenticationManager authenticationManager;

    /**
     * not used
     */
    private SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        // disable default security
        http.csrf().disable();

        // add custom security into filter chain like jwt
        // omitted for sake of brevity and use of testing

        return http.build();
    }

}
