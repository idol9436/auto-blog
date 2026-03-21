package com.sangsang.autoblog.adapter.out.persistence.adapter;

import org.springframework.stereotype.Repository;

import com.sangsang.autoblog.adapter.out.persistence.entity.UserOAuthEntity;
import com.sangsang.autoblog.adapter.out.persistence.repository.UserOAuthRepository;
import com.sangsang.autoblog.domain.model.User;
import com.sangsang.autoblog.domain.port.out.UserOAuthRepositoryPort;

@Repository
public class UserOAuthAdapter implements UserOAuthRepositoryPort {

    private final UserOAuthRepository userOAuthRepository;

    public UserOAuthAdapter(UserOAuthRepository userOAuthRepository) {
        this.userOAuthRepository = userOAuthRepository;
    }

    @Override
    public boolean existsByProviderAndProviderId(String provider, String providerId) {
        return userOAuthRepository.existsByProviderAndProviderId(provider, providerId);
    }

    @Override
    public UserOAuthEntity save(User user, Long originUserId) {        
        UserOAuthEntity savedEntity = userOAuthRepository.save(new UserOAuthEntity(originUserId, user.provider, user.providerId));
        return savedEntity;
    }
    
}
