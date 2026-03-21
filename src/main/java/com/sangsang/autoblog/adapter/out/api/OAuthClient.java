package com.sangsang.autoblog.adapter.out.api;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.sangsang.autoblog.application.command.SignupCommand;
import com.sangsang.autoblog.domain.model.User;
import com.sangsang.autoblog.domain.port.out.GithubOAuthPort;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class OAuthClient implements GithubOAuthPort {


    private final Dotenv dotenv;
    private final String GITHUB_ACCESS_TOKEN_URL;
    private final String GITHUB_CLIENT_ID;
    private final String GITHUB_CLIENT_SECRET;
    private final String GITHUB_USER_INFO_URL;

    public OAuthClient() {
        this.dotenv = Dotenv.load();
        this.GITHUB_ACCESS_TOKEN_URL = dotenv.get("GITHUB_ACCESS_TOKEN_URL");
        this.GITHUB_CLIENT_ID = dotenv.get("GITHUB_CLIENT_ID");
        this.GITHUB_CLIENT_SECRET = dotenv.get("GITHUB_CLIENT_SECRET");
        this.GITHUB_USER_INFO_URL = dotenv.get("GITHUB_USER_INFO_URL");
    }

    @Override
    public String getGithubAccessToken(String code) {

        WebClient webClient = WebClient.create();

        Map<String, Object> response = webClient.post()
            .uri(GITHUB_ACCESS_TOKEN_URL)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(Map.of(
                "client_id", GITHUB_CLIENT_ID,
                "client_secret", GITHUB_CLIENT_SECRET,
                "code", code
            ))
            .retrieve()
            .bodyToMono(Map.class)
            .block();

        String accessToken = (String) response.get("access_token");

        return accessToken;
    }

    @Override
    public User getGithubUserInfo(String accessToken) {

        WebClient webClient = WebClient.create();

        Map<String, Object> response = webClient.get()
            .uri(GITHUB_USER_INFO_URL)
            .header(HttpHeaders.AUTHORIZATION, "token " + accessToken)
            .retrieve()
            .bodyToMono(Map.class)
            .block();

        SignupCommand cmd = new SignupCommand(
            null, // id
            (String) response.get("login"), // username
            null, // password
            (String) response.get("email"), // email
            null, // extraInfo
            true, // agreeToTerms
            "GITHUB", // provider
            String.valueOf(response.get("id")) // providerId
        );
        User user = User.signupOAuthFrom(cmd);
        return user;
    }
    
}
