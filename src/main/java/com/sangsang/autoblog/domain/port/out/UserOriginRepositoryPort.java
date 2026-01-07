package com.sangsang.autoblog.domain.port.out;

import com.sangsang.autoblog.domain.model.User;

public interface UserOriginRepositoryPort{

    //signup
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    User save(User user);

    //signin
    User findByUsernameAndPassword(String username, String password);
}
