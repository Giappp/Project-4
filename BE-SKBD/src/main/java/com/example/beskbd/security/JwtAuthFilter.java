package com.example.beskbd.security;

import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.entities.User;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.services.JwtService;
import com.example.beskbd.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String LOGIN_PATH = "/login";

    private final JwtService jwtService;
    private final UserService userService;
    private final ObjectMapper objectMapper;  // Inject ObjectMapper

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = extractTokenFromHeader(request);
            if (token != null) {
                logger.debug("Token receive: " + token);
                processToken(token, request, response);
            }
        } catch (JwtAuthenticationException e) {
            handleAuthenticationError(response, e.getMessage());
            return;
        } catch (Exception e) {
            handleAuthenticationError(response, "Authentication failed");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private void processToken(String token, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (token.isEmpty() || !jwtService.isValidJwtToken(token)) {
            throw new JwtAuthenticationException("Invalid token");
        }

        String userName = jwtService.extractUserName(token);
        User user = userService.loadUserByUsername(userName);

        if (jwtService.validateToken(token, user)) {
            throw new JwtAuthenticationException("Token validation failed");
        }

        if (isLoginRequest(request)) {
            logger.debug("Login user");
            handleAlreadyLoggedInUser(response);
            return;
        }

        setSecurityContext(user, request);
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        return request.getRequestURI().contains(LOGIN_PATH);
    }

    private void setSecurityContext(User user, HttpServletRequest request) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
            logger.debug("Set security context for user: " + user.getUsername() + " with authorities: " + user.getAuthority().getAuthority());
        }
    }

    private void handleAlreadyLoggedInUser(HttpServletResponse response) throws IOException {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .errorCode(ErrorCode.ALREADY_LOGIN.getCode())
                .errorMessage("User is already logged in.")
                .build();

        writeJsonResponse(response, HttpServletResponse.SC_BAD_REQUEST, apiResponse);
    }

    private void handleAuthenticationError(HttpServletResponse response, String message)
            throws IOException {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .errorCode(ErrorCode.AUTHENTICATION_FAILED.getCode())
                .errorMessage(message)
                .build();

        writeJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, apiResponse);
    }

    private void writeJsonResponse(HttpServletResponse response, int status, ApiResponse<?> apiResponse)
            throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
