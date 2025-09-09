package org.culpan.champ3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev")
public class DevSecurityConfig {
  @SuppressWarnings("unused")
  @Bean
  @Order(1) // must be before the catch-all app chain
  SecurityFilterChain h2ConsoleChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/h2-console", "/h2-console/**") // <-- include both
        .authorizeHttpRequests(a -> a.anyRequest().permitAll())
        .csrf(csrf -> csrf.disable()) // console posts forms
        .headers(h -> h.frameOptions(f -> f.sameOrigin())); // console uses frames
    return http.build();
  }
}