package com.sangsang.autoblog.adapter.in.web.dto;

import com.sangsang.autoblog.domain.model.User;

import lombok.Setter;

@Setter
public class SignupReqeustDTO {

    private String email;
    private String password;
    private String extraInfo;
    private String nickname;
    private boolean agreeToTerms;

    public User toUserDomain() {
        return User.signupWithOrigin(nickname, password, email, extraInfo, agreeToTerms);
    }
}
