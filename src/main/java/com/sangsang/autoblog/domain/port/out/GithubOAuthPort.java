package com.sangsang.autoblog.domain.port.out;

import com.sangsang.autoblog.domain.model.User;

public interface GithubOAuthPort {
    
    String getGithubAccessToken(String code);
    User getGithubUserInfo(String accessToken);
}
