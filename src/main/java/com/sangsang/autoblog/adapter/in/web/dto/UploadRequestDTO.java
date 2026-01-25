package com.sangsang.autoblog.adapter.in.web.dto;

import com.sangsang.autoblog.application.command.UploadCommand;

public record UploadRequestDTO(
    String type,
    String token, 
    String owner, 
    String repo, 
    String path, 
    String commitMsg,
    String content,
    String sha
) {
    public UploadCommand toCommand() {
        return new UploadCommand(
            type,
            token, 
            owner, 
            repo, 
            path, 
            commitMsg,
            content,
            sha
        );
    }
}
