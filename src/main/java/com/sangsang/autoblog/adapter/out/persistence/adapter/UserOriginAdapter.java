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
    public User findByUserName(String userName) {
         UserOriginEntity userEntity = userOriginRepository.findByUserName(userName);
         if (userEntity != null) {
             return UserOriginEntity.toDomain(userEntity);
         }
        return null;
    }
}
