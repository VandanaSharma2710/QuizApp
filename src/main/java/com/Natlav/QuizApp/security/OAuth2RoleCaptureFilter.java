package com.Natlav.QuizApp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class OAuth2RoleCaptureFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        String role = request.getParameter("role");

        if (role != null) {
            Cookie cookie = new Cookie("OAUTH2_ROLE", role);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(180); // 3 minutes
            response.addCookie(cookie);

            System.out.println("ROLE STORED = " + role);
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/oauth2/authorization");
    }
}
