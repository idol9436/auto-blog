package com.sangsang.autoblog.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sangsang.autoblog.adapter.out.persistence.entity.UserOriginEntity;

public interface UserOriginRepository extends JpaRepository<UserOriginEntity, Long> {

    public UserOriginEntity findByUserName(String userName);
} 
