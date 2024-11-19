package com.example.beskbd.security;

import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
//        logger.error("Unauthorized error for path: {}", request.getRequestURI());

        ErrorCode errorCode = determineErrorCode(authException);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .errorCode(errorCode.getCode())
                .errorMessage(buildErrorMessage(request, authException))
                .build();

        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }

    private ErrorCode determineErrorCode(AuthenticationException authException) {
        if (authException instanceof BadCredentialsException) {
            return ErrorCode.INVALID_CREDENTIALS;
        } else if (authException instanceof InsufficientAuthenticationException) {
            return ErrorCode.UNAUTHENTICATED;
        }
        return ErrorCode.AUTHENTICATION_FAILED;
    }

    private String buildErrorMessage(HttpServletRequest request, AuthenticationException authException) {
        String path = request.getRequestURI();
        String message = authException.getMessage();
        return String.format("Access denied for path '%s': %s", path, message);
    }
}
