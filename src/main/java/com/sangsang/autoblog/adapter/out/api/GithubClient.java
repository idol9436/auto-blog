package com.sangsang.autoblog.adapter.out.api;

import java.util.Base64;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.sangsang.autoblog.domain.model.GithubInfo;
import com.sangsang.autoblog.domain.port.out.GithubRestApiPort;

import reactor.core.publisher.Mono;

@Component
public class GithubClient implements GithubRestApiPort{
    private final WebClient webClient;

    public GithubClient(WebClient.Builder builder) {
        this.webClient = builder
        .baseUrl("https://api.github.com")
        .build();
    }

    @Override
    public Mono<String> getExistInfo(GithubInfo githubInfo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}/contents/{path}", githubInfo.owner, githubInfo.repo, githubInfo.path)
            .headers(h -> h.setBearerAuth(githubInfo.token))
            .headers(h -> h.set("X-GitHub-Api-Version", "2022-11-28"))
            .exchangeToMono(response -> {
                return response.bodyToMono(String.class)
                                .flatMap(body -> {
                                    if (response.statusCode() == HttpStatus.OK ||
                                        response.statusCode() == HttpStatus.CREATED) {
                                        return Mono.just(body);
                                    }
                                    return Mono.error(new RuntimeException(body));
                                });
        });
    }
  

    @Override
    public Mono<String> updateToGithub(GithubInfo githubInfo) {
        return webClient.put()
            .uri("/repos/{owner}/{repo}/contents/{path}", githubInfo.owner, githubInfo.repo, githubInfo.path)
            .headers(h -> h.setBearerAuth(githubInfo.token))
            .headers(h -> h.set("X-GitHub-Api-Version", "2022-11-28"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(Map.of(
                "message", githubInfo.commitMsg,
                "content", Base64.getEncoder().encodeToString(githubInfo.content.getBytes()),
                "sha", githubInfo.sha
            ))
            .exchangeToMono(response -> {
                return response.bodyToMono(String.class)
                                .flatMap(body -> {
                                    if (response.statusCode() == HttpStatus.OK ||
                                        response.statusCode() == HttpStatus.CREATED) {
                                        return Mono.just(body);
                                    }
                                    return Mono.error(new RuntimeException(body));
                                });
        });
    }

    @Override
    public Mono<String> createToGithub(GithubInfo githubInfo) {
        return webClient.put()
            .uri("/repos/{owner}/{repo}/contents/{path}", githubInfo.owner, githubInfo.repo, githubInfo.path)
            .headers(h -> h.setBearerAuth(githubInfo.token))
            .headers(h -> h.set("X-GitHub-Api-Version", "2022-11-28"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(Map.of(
                "message", githubInfo.commitMsg,
                "content", Base64.getEncoder().encodeToString(githubInfo.content.getBytes())
            ))
            .exchangeToMono(response -> {
                return response.bodyToMono(String.class)
                                .flatMap(body -> {
                                    if (response.statusCode() == HttpStatus.OK ||
                                        response.statusCode() == HttpStatus.CREATED) {
                                        return Mono.just(body);
                                    }
                                    return Mono.error(new RuntimeException(body));
                                });
        });
    }

}
