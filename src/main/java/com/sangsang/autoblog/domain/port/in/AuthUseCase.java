package com.sangsang.autoblog.domain.port.in;

import com.sangsang.autoblog.application.command.SignupCommand;
import com.sangsang.autoblog.domain.model.User;

public interface AuthUseCase {
    User signup(SignupCommand cmd);
}
