package com.Natlav.QuizApp.security;

import com.Natlav.QuizApp.entities.User;
import com.Natlav.QuizApp.enums.RoleType;
import com.Natlav.QuizApp.repositories.UsersRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {


    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name  = oAuth2User.getAttribute("name");
        OAuth2AuthenticationToken oauthToken =
                (OAuth2AuthenticationToken) authentication;


        String role = extractRoleFromCookie(request);

        RoleType finalRole =
                "GAMEMASTER".equalsIgnoreCase(role)
                        ? RoleType.ROLE_GAMEMASTER
                        : RoleType.ROLE_PLAYER;

        User user = usersRepository.findByEmail(email).orElse(null);

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setUsername(name);
            user.setProvider("Google");
            user.setRole(finalRole);
        } else {
            if (user.getUsername() == null || user.getUsername().isBlank()) {
                user.setUsername(name);
            }
        }

        usersRepository.save(user);

        System.out.println("Role = " + role);
        System.out.println("FINAL ROLE = " + user.getRole());

        String jwt = jwtUtil.generateToken(user);

        String encodedJwt = java.net.URLEncoder.encode(
                jwt, java.nio.charset.StandardCharsets.UTF_8
        );

        response.sendRedirect(
                "http://localhost:4200/oauth-success?token=" + encodedJwt
        );
    }
    private String extractRoleFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return "PLAYER";

        for (Cookie cookie : request.getCookies()) {
            if ("OAUTH2_ROLE".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "PLAYER";
    }
}
