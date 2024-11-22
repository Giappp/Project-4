package com.example.beskbd.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private Set<Role> authorities;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String resetToken;

    private boolean accountNonExpired;
    private boolean isEnabled;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
    private String imageUrl;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany
    private List<Review> reviews;
>>>>>>> Stashed changes

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(authority);
    }


>>>>>>> Stashed changes
}
