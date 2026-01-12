package com.sangsang.autoblog.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sangsang.autoblog.adapter.out.persistence.entity.UserOriginEntity;

public interface UserOriginRepository extends JpaRepository<UserOriginEntity, Long> {

    public UserOriginEntity findByUsername(String username);

    public boolean existsByEmail(String email);
    public boolean existsByUsername(String username);
    
} 