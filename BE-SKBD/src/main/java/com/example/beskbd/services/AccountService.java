package com.example.beskbd.services;

import com.example.beskbd.dto.response.AccountResponse;
import com.example.beskbd.entities.User;
import com.example.beskbd.exception.AppException;
import com.example.beskbd.exception.ErrorCode;
import com.example.beskbd.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(AccountService.class);

    public AccountResponse getAccountDetails() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            logger.info("Retrieving account details for user: {}", user.getUsername());
            return AccountResponse.builder()
                    .activated(user.isEnabled())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .authorities(List.of(user.getAuthority().getAuthority()))
                    .login(user.getUsername())
                    .imageUrl(user.getImageUrl()) // Assuming getImageUrl() method exists
                    .build();
        }

        logger.warn("Unauthenticated access attempt.");
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
}
