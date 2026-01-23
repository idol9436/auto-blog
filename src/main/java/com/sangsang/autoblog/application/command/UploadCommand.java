package com.sangsang.autoblog.application.command;

public record UploadCommand(
    String type,
    String token, 
    String owner, 
    String repo, 
    String path, 
    String commitMsg, 
    String content
) {} 