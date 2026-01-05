package com.sangsang.autoblog.adapter.in.web.dto;

import com.sangsang.autoblog.domain.model.User;

import lombok.Setter;

@Setter
public class SigninReqeustDTO {

    private String userId;
    private String password;
    private boolean rememberMe;

    public User toSigninDomain() {
        return User.signinWithOrigin(userId, password, rememberMe);
    }

}
