package com.sangsang.autoblog.adapter.in.web.dto;

import java.time.LocalDateTime;
import com.sangsang.autoblog.domain.model.PostContent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PromptResponseDTO {
    
    private String HTMLbyMarkdown;
    private String title;
    private String body;
    private String[] tags;
    private String imageUrl;
    private String summary;
    private LocalDateTime date;

    public static PromptResponseDTO from(PostContent content) {
        PromptResponseDTO dto = new PromptResponseDTO();
        dto.HTMLbyMarkdown = content.mdText;
        dto.title = content.title;
        dto.body = content.body;
        dto.tags = content.tags;
        dto.imageUrl = content.imageUrl;
        dto.summary = content.summary;
        dto.date = content.date;
        return dto;
    }
}