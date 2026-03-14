package com.eindopdracht.backend.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="users")
public class User {

    @Id
    private UUID id;
    @Setter
    @Column(nullable = false, unique = true)
    private String email;
    @Setter
    @Column(nullable = false)
    private String password;
    @Setter
    private String name;

    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    public static User create(String name, String email, String password, Set<Role> roles) {
        User user = new User();
        user.name = name;
        user.email = email;
        user.password = password;
        user.roles = roles;
        return user;
    }

    @PrePersist
    public void generateId(){
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
