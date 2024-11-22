package com.example.beskbd.config;

import com.example.beskbd.security.JwtAuthFilter;
import com.example.beskbd.security.JwtAuthenticationEntryPoint;
import com.example.beskbd.services.UserService;
<<<<<<< Updated upstream
=======
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;
>>>>>>> Stashed changes
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
<<<<<<< Updated upstream
public class SecurityConfig {
    private static final String[] WHITE_LIST_URL = {
            "auth/registration",
            "auth/token",
<<<<<<< Updated upstream
=======
            "paypal/pay",
            "auth/forgot-password",
            "auth/reset-password",
            "auth/registration",
            "auth/signout",
            "auth/login"
>>>>>>> Stashed changes
=======
public class SecurityConfig  {
    @Value("${application.security.jwt.secret-key-v2}")
    private String secretKey;
    private static final String[] WHITE_LIST_URL = {
            "/auth/registration",
            "/auth/login",
            "/auth/token",
            "/api/test",
            "/api/getTest",
            "/paypal/pay",
            "/error",
            "/api/categories",
            "/api/products/",
            "/api/products/gender"  // Ensure this path is included

>>>>>>> Stashed changes
    };
    private final JwtAuthFilter jwtAuthFilter;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Disabling CSRF as we use JWT which is immune to CSRF
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITE_LIST_URL)
                        .permitAll()
                        .anyRequest()
                        .authenticated()) // All other requests must be authenticated
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
<<<<<<< Updated upstream
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
=======

                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(this.oidcUserService())))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) {
                OidcUser oidcUser = super.loadUser(userRequest);
                String jwt = generateJwt(oidcUser);

                // You might want to store the JWT in a cookie or return it in a response body
                // Here’s a simple example of how to return the JWT:
                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
                if (response != null) {
                    response.setHeader("Authorization", "Bearer " + jwt);
                }

                return oidcUser; // Return the OidcUser or any custom user object
            }
        };
    }

    private String generateJwt(OidcUser oidcUser) {
        String jwt = Jwts.builder()
                .setSubject(oidcUser.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, secretKey) // Use injected secret key
                .compact();
        return jwt;
    }
>>>>>>> Stashed changes

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource
                = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new org.springframework.web.filter.CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

}
