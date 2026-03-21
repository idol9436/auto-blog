package com.sangsang.autoblog.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import com.sangsang.autoblog.domain.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_oauth",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"provider", "provider_id"})
})
public class UserOAuthEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_user_id", nullable = false)
    private Long originUserId;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public UserOAuthEntity(Long id, String provider, String providerId) {
        this.originUserId = id;
        this.provider = provider;
        this.providerId = providerId;
        this.createdAt = LocalDateTime.now();
    }   

    public static UserOAuthEntity fromDomain(User user) {
        return new UserOAuthEntity(user.id, user.provider, user.providerId);
    }
    // TODO public User toDomain(){}
}
