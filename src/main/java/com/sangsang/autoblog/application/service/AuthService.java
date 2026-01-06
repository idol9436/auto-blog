package com.sangsang.autoblog.application.service;

import org.springframework.stereotype.Service;

import com.sangsang.autoblog.domain.exception.DuplicateEmailException;
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
        User foundUser = userOriginRepositoryPort.findByUserNameAndPassword(
            signinInfo.userName,
            signinInfo.password
        );
        if(foundUser == null) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return foundUser;
    }

    @Override
    public User signup(User newUser) {

        try {
            // TODO : 예외처리 구체화
            User exsistUser = userOriginRepositoryPort.findByUserName(newUser.userName);
            if(exsistUser != null) {
                throw new IllegalArgumentException("User already exists");
            }

        } catch (DuplicateEmailException e) {
            // TODO: handle exception
            throw new DuplicateEmailException("Duplicate email found during signup");
        } catch (Exception e) {
            // TODO: handle exception
        }

        return userOriginRepositoryPort.save(newUser);
    }
}
