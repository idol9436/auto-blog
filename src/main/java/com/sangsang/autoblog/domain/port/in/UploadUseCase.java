package com.sangsang.autoblog.domain.port.in;

import org.springframework.http.ResponseEntity;

import com.sangsang.autoblog.application.command.UploadCommand;

import reactor.core.publisher.Mono;

public interface UploadUseCase {
    
    public Mono<ResponseEntity<String>> upload(UploadCommand uploadCommand);
    public Mono<ResponseEntity<String>> findExist(UploadCommand uploadCommand);

}
