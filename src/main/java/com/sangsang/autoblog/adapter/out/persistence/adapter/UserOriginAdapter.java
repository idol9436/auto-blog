package com.sangsang.autoblog.adapter.out.persistence.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sangsang.autoblog.adapter.out.persistence.entity.UserOriginEntity;
import com.sangsang.autoblog.adapter.out.persistence.repository.UserOriginRepository;
import com.sangsang.autoblog.domain.model.User;
import com.sangsang.autoblog.domain.port.out.UserOriginRepositoryPort;

@Repository
public class UserOriginAdapter implements UserOriginRepositoryPort {

    private final UserOriginRepository userOriginRepository;

    public UserOriginAdapter(UserOriginRepository userOriginRepository) {
        this.userOriginRepository = userOriginRepository;
    }

    @Override
    public User save(User user) {
        UserOriginEntity savedEntity = userOriginRepository.save(UserOriginEntity.fromDomain(user));
        return savedEntity.toOriginUserDomain();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userOriginRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userOriginRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        System.out.println("username: "+username);
        Optional<User> optUser;
        try {
            UserOriginEntity userEntity = userOriginRepository.findByUsername(username);
            optUser = Optional.of(userEntity.toOriginUserDomain()); 
        } catch (Exception e) {
            optUser = null;
        }
        return optUser;
    }
}
