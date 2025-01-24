package com.example.Contact_manager_web.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails {

    @Id
    private String userId;

    @Column(name = "userName", nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Getter(AccessLevel.NONE)
    @Column(nullable = false)
    private String password;

    @Column(length = 1000)
    private String about;

    @Column(length = 1000)
    private String profilePic;

    private String phoneNumber;

    @Getter(AccessLevel.NONE)
    private boolean enabled = true;

    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    @Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF;
    private String provideId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @Builder
    public static User create(String userId, String name, String email, String password, String about,
                              String profilePic, String phoneNumber, boolean enabled, boolean emailVerified,
                              boolean phoneVerified, Providers provider, String provideId) {
        User user = new User();
        user.userId = userId;
        user.name = name;
        user.email = email;
        user.password = password;
        user.about = about;
        user.profilePic = profilePic;
        user.phoneNumber = phoneNumber;
        user.enabled = enabled;
        user.emailVerified = emailVerified;
        user.phoneVerified = phoneVerified;
        user.provider = provider;
        user.provideId = provideId;
        return user;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled; // Corrected implementation
    }



    // getter AND Setter Method
}
