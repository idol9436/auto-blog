package com.sangsang.autoblog.domain.port.out;

import com.sangsang.autoblog.adapter.out.persistence.entity.UserOAuthEntity;
import com.sangsang.autoblog.domain.model.User;

public interface UserOAuthRepositoryPort {

    boolean existsByProviderAndProviderId(String provider, String providerId);
    UserOAuthEntity save(User user, Long originUserId);

}
