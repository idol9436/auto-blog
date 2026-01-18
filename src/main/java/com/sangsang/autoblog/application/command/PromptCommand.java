package com.sangsang.autoblog.application.command;

import org.springframework.web.multipart.MultipartFile;

public record PromptCommand(
    String promptText,
    MultipartFile[] promptFiles
) {} 