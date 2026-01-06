package com.sangsang.autoblog.domain.port.out;

import com.sangsang.autoblog.domain.model.User;

public interface UserOriginRepositoryPort{
    User save(User user);
    User findByUserName(String userName);
    User findByUserNameAndPassword(String userName, String password);
}
