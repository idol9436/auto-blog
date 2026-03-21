package com.sangsang.autoblog.domain.port.in;

import com.sangsang.autoblog.application.command.SignupCommand;
import com.sangsang.autoblog.domain.model.User;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthUseCase {
    User signup(SignupCommand cmd);
    void signinByGithub(String code, HttpServletRequest request);
}
