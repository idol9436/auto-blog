package com.sangsang.autoblog.domain.port.in;

import com.sangsang.autoblog.application.command.UploadCommand;

import reactor.core.publisher.Mono;

public interface UploadUseCase {
    
    public Mono<String> upload(UploadCommand uploadCommand);
    public Mono<String> findExist(UploadCommand uploadCommand);

}
