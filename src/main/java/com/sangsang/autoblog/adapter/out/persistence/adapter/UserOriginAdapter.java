package com.sangsang.autoblog.adapter.out.persistence.adapter;

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
        UserOriginEntity userEntity = UserOriginEntity.fromDomain(user);

        UserOriginEntity savedEntity = userOriginRepository.save(userEntity);
        return UserOriginEntity.toDomain(savedEntity);
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
    public User findByUsernameAndPassword(String username, String password) {
        UserOriginEntity userEntity = userOriginRepository.findByUsernameAndPassword(username, password);
        if (userEntity != null) {
            return UserOriginEntity.toDomain(userEntity);
        }
       return null;
    }
}
