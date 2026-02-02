package com.Natlav.QuizApp.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;


    @Autowired
    private CorsConfigurationSource corsConfigurationSource;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors->cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error", "/oauth2/**", "/login/**",  "/api/login/google").permitAll()
                        .requestMatchers("/api/master/**").hasRole("GAMEMASTER")
                        .requestMatchers("/api/player/**").hasAnyRole("PLAYER", "GAMEMASTER")
                        .anyRequest().authenticated())

                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo ->userInfo.userService(customOAuth2UserService))
                        .successHandler((request, response, authentication) -> {

                            boolean isGameMaster = authentication.getAuthorities().stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_GAMEMASTER"));

                            if (isGameMaster) {

                                response.sendRedirect("http://localhost:4200/master/quiz-management");
                            } else {
                                response.sendRedirect("http://localhost:4200/player");
                            }
                        })
                )
                .logout(
                        logout -> logout.logoutSuccessUrl("/")
                ).exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                );


            return http.build();
    }
}
