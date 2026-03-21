package com.sangsang.autoblog.domain.port.out;

import java.util.Optional;

import com.sangsang.autoblog.adapter.out.persistence.entity.UserOAuthEntity;
import com.sangsang.autoblog.domain.model.User;

public interface UserOAuthRepositoryPort {

    boolean existsByProviderAndProviderId(String provider, String providerId);
    Optional<Long> findByProviderAndProviderId(String provider, String providerId);
    UserOAuthEntity save(User user, Long originUserId);

}
