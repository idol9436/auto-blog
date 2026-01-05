package com.sangsang.autoblog.application.service;

import org.springframework.stereotype.Service;

import com.sangsang.autoblog.domain.model.User;

import com.sangsang.autoblog.domain.port.in.AuthUseCase;
import com.sangsang.autoblog.domain.port.out.UserHistoryRepositoryPort;
import com.sangsang.autoblog.domain.port.out.UserOAuthRepositoryPort;
import com.sangsang.autoblog.domain.port.out.UserOriginRepositoryPort;

@Service
public class AuthService implements AuthUseCase{

    private final UserHistoryRepositoryPort userRepositoryPort;
    private final UserOriginRepositoryPort userOriginRepositoryPort;
    private final UserOAuthRepositoryPort userOAuthRepositoryPort;

    public AuthService(
        UserHistoryRepositoryPort userRepositoryPort,
        UserOriginRepositoryPort userOriginRepositoryPort,
        UserOAuthRepositoryPort userOAuthRepositoryPort
    ) {
        this.userRepositoryPort = userRepositoryPort;
        this.userOriginRepositoryPort = userOriginRepositoryPort;
        this.userOAuthRepositoryPort = userOAuthRepositoryPort;
    }

    @Override
    public User signin(User signinInfo) {
        // TODO : 인증 로직 구현
        return null;
    }

    @Override
    public User signup(User newUser) {
        // TODO : 회원가입 로직 구현

        User exsistUser = userOriginRepositoryPort.findByUserName(newUser.userName);
        if(exsistUser != null) {
            throw new IllegalArgumentException("User already exists");
        }

        return userOriginRepositoryPort.save(newUser);
    }
}
