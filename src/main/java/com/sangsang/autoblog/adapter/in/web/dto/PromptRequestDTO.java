package com.sangsang.autoblog.adapter.in.web.dto;

import org.springframework.web.multipart.MultipartFile;

import com.sangsang.autoblog.domain.model.Prompt;

import lombok.Setter;

@Setter
public class PromptRequestDTO {

    private String promptText;
    private MultipartFile[] promptFiles;

    public Prompt toPromptDomain() {
        return new Prompt(promptText, promptFiles);
    }
}
