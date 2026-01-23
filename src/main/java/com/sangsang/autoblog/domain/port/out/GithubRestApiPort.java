package com.sangsang.autoblog.domain.port.out;

import com.sangsang.autoblog.domain.model.GithubInfo;

import reactor.core.publisher.Mono;

public interface GithubRestApiPort {
    public Mono<String> commitToGithub(GithubInfo info);
}
