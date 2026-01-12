package com.sangsang.autoblog.domain.port.out;

import java.util.Optional;

import com.sangsang.autoblog.domain.model.User;

public interface UserOriginRepositoryPort{

    //signup
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User save(User user);

    //signin
    Optional<User> findByUsername(String username);
}
