package com.sangsang.autoblog.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sangsang.autoblog.adapter.out.persistence.entity.UserOAuthEntity;

public interface UserOAuthRepository extends JpaRepository<UserOAuthEntity, Long> {
    boolean existsByProviderAndProviderId(String provider, String providerId);
} 
