package com.sangsang.autoblog.adapter.in.web.dto;

import org.springframework.web.multipart.MultipartFile;

import com.sangsang.autoblog.application.command.PromptCommand;

public record PromptRequestDTO(
    
    String promptText,
    MultipartFile[] promptFiles

) {
    public PromptCommand toCommand() {
        return new PromptCommand(
            promptText, 
            promptFiles
        );
    }

}