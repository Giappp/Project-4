package com.example.beskbd.security;

import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.services.JwtService;
import com.example.beskbd.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String userName;
            if (!token.isEmpty() && jwtService.isValidJwtToken(token)) {
                userName = jwtService.extractUserName(token);
                UserDetails user = userService.loadUserByUsername(userName);
                var checkUserToken = jwtService.validateToken(token, user);
                if (checkUserToken && request.getRequestURI().contains("/login")) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // or another status code you prefer
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    ApiResponse<?> apiResponse = ApiResponse.builder()
                            .errorCode(ErrorCode.ALREADY_LOGIN.getCode())
                            .errorMessage("User is already logged in.")
                            .build();
                    response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
                    response.flushBuffer();
                    return;
                } else if (checkUserToken && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authToken
                            = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
