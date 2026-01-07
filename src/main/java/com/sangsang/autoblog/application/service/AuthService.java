package com.sangsang.autoblog.application.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sangsang.autoblog.domain.exception.DuplicateException;
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
        // TODO : 인증로직 구제화
        User foundUser = userOriginRepositoryPort.findByUsernameAndPassword(
            signinInfo.username,
            signinInfo.password
        );
        if(foundUser == null) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return foundUser;
    }

    @Override
    public User signup(User newUser) {

        if(userOriginRepositoryPort.existsByUsername(newUser.username)){
            throw new IllegalArgumentException("Username already exists");
        }

        if(userOriginRepositoryPort.existsByEmail(newUser.email)){
            throw new IllegalArgumentException("Email already exists");
        }
        
        try {
            return userOriginRepositoryPort.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateException("Duplicate info found during signup");
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }
}
