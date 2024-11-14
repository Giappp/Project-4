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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    UserRepository userRepository;

    public AccountResponse getAccountDetails() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return AccountResponse.builder()
                    .activated(user.isEnabled())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .authorities(List.of(user.getAuthority().getAuthority()))
                    .login(user.getUsername())
                    .imageUrl(null)
                    .build();
        }
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
}
