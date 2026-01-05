package com.sangsang.autoblog.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import com.sangsang.autoblog.domain.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_origin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOriginEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "status")
    private String status;

    public UserOriginEntity(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.status = "ACTIVE";
    }

    public static UserOriginEntity fromDomain(User user) {
        return new UserOriginEntity(
            user.userName,
            user.password,
            user.email
        );
    }

    public static User toDomain(UserOriginEntity entity) {
        return User.signupWithOrigin(
            entity.getUserName(),
            entity.getPassword(),
            entity.getEmail(),
            null,
            true
        );
    }
}
