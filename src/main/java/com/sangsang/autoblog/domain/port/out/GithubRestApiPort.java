package com.sangsang.autoblog.domain.port.out;

import org.springframework.http.ResponseEntity;

import com.sangsang.autoblog.domain.model.GithubInfo;

import reactor.core.publisher.Mono;

public interface GithubRestApiPort {
    public Mono<ResponseEntity<String>>  getExistInfo(GithubInfo info);
    public Mono<ResponseEntity<String>>  createToGithub(GithubInfo info);
    public Mono<ResponseEntity<String>>  updateToGithub(GithubInfo info);
}
