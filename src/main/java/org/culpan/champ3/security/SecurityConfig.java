package org.culpan.champ3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        // uses bcrypt; DelegatingPasswordEncoder will honor {bcrypt} prefix
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    SecurityFilterChain appChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**",
                                "/register", "/register/**", "/verify-email", "/verify-email/**",
                                "/favicon.ico",
                                "/error", "/h2-console", "/h2-console/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout"))
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console", "/h2-console/**"))
                .headers(h -> h.frameOptions(f -> f.sameOrigin()));
        return http.build();
    }
}