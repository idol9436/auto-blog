package com.sangsang.autoblog.adapter.out.api;

import java.util.Base64;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.sangsang.autoblog.domain.model.GithubInfo;
import com.sangsang.autoblog.domain.port.out.GithubRestApiPort;

import reactor.core.publisher.Mono;

/*
 * Github REST API Reference
 * https://docs.github.com/en/rest/repos/contents?apiVersion=2022-11-28 : contents
 * https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28 : repo
 */
@Component
public class GithubClient implements GithubRestApiPort{
    private final WebClient webClient;

    public GithubClient(WebClient.Builder builder) {
        this.webClient = builder
        .baseUrl("https://api.github.com")
        .build();
    }

    @Override
    public Mono<ResponseEntity<String>> getExistInfo(GithubInfo githubInfo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}/contents/{path}", githubInfo.owner, githubInfo.repo, githubInfo.path)
            .headers(h -> h.setBearerAuth(githubInfo.token))
            .headers(h -> h.set("X-GitHub-Api-Version", "2022-11-28"))
            .exchangeToMono(response -> {
                return response.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .map(body ->
                                    ResponseEntity
                                        .status(response.statusCode())
                                        .body(body)
                                );
        });
    }
  

    @Override
    public Mono<ResponseEntity<String>> updateToGithub(GithubInfo githubInfo) {
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
                                .defaultIfEmpty("")
                                .map(body ->
                                    ResponseEntity
                                        .status(response.statusCode())
                                        .body(body)
                                );
        });
    }

    @Override
    public Mono<ResponseEntity<String>> createToGithub(GithubInfo githubInfo) {
        return webClient.put()
            .uri("/repos/{owner}/{repo}/contents/{path}", githubInfo.owner, githubInfo.repo, githubInfo.path)
            .headers(h -> h.setBearerAuth(githubInfo.token))
            .headers(h -> h.set("Accept", "application/vnd.github.json"))
            .headers(h -> h.set("X-GitHub-Api-Version", "2022-11-28"))
            .bodyValue(Map.of(
                "message", githubInfo.commitMsg,
                "content", Base64.getEncoder().encodeToString(githubInfo.content.getBytes())
            ))
            .exchangeToMono(response -> {
                return response.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .map(body ->
                                    ResponseEntity
                                        .status(response.statusCode())
                                        .body(body)
                                );
        });
    }

    // TODO : Mono<String> -> Mono<ResponseEntity<String> 변경 요망
    /*
    404 -> 500 으로 Mono 리턴되는 문제 수정 필요, 앞단으로 책임 전가
    case 1 : 잘못된 토큰 -> 401 Unauthorized(Find) -> 401 Unauthorized(Create)
    case 2 : 잘못된 owner -> 404 Not Found(Find) -> 404 Not Found(Create)
    case 3 : 잘못된 repo -> 404 Not Found(Find) -> 404 Not Found(Create)
    case 4 : 존재하지않는 path/file.md -> 404 Not Found(Find) -> 201 Created(Create)
    case 5 : 정상 처리 -> 200 OK(Find) -> 200 OK(Update)
    */

    /*
    TODO : 앞단 전략
    401 : 잘못된 토큰 -> 토큰 확인 유도
    Find 404 : 정상+비정상 결과 혼재 -> 200이 아니면 모두 Create로 이동
    Find 200 : 정상 결과 -> Update 진행
    Create 404 : 잘못된 owner or repo -> owner/repo 확인 및 생성 유도
    Create 201 : 정상 결과 -> 완료
    500 : 기타 에러 -> 재시도 또는 관리자 문의 유도
    */

    /*
    Repository management API
    1. 직접 생성 버튼
    2. create 404케이스에서 throw 
    3. 이미 존재하는지 확인하기 버튼
    4. 생성 성공 후 Content 작성하기 버튼 유도
     */
}
