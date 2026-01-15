package com.sangsang.autoblog.domain.model;

import org.springframework.web.multipart.MultipartFile;

public class Prompt {

    public String promptText;
    public final MultipartFile[] promptFiles;

    public Prompt(String promptText, MultipartFile[] promptFiles) {
        System.out.println("new promptText: " + promptText);
        this.promptText = promptText;
        this.promptFiles = promptFiles;
    }

    public Prompt parsingToTitle(){
        this.promptText = "Generate a blog title in a few words for the following prompt [%s] ".formatted(promptText);
        return this;
    }

    public Prompt parsingToBody(){
        this.promptText = "Generate a detailed blog body in a 2000 bytes for the following prompt [%s] ".formatted(promptText);
        return this;
    }

    public Prompt parsingToTags(){
        this.promptText = "Generate relevant tags with comma separated for the following blog prompt [%s] ".formatted(promptText);
        return this;
    }   

    public Prompt parsingToSummary(){
        this.promptText = "Generate a concise summary in a 200 bytes for the following blog prompt [%s] ".formatted(promptText);
        return this;
    }

    public Prompt parsingToImageUrl(){
        this.promptText = "Generate an image description use freeSite for the following blog prompt [%s] ".formatted(promptText);
        return this;
    }

    public Prompt parsingToMarkdownText(){
        this.promptText= """
            You are a technical blogger.
            
            Write a Korean technical blog post in Markdown format based on the topic below.
            
            Topic:
            [%s]
            
            Requirements:
            - Style: Today I Learned / technical blog
            - Tone: concise, clear, practical
            - Length: within one A4 page
            - Output ONLY valid Markdown (no explanations)
            
            Structure:
            1. Title (max 30 characters)
            2. Summary (2~3 sentences)
            3. Body
                - Use headings (##)
                - Include code blocks if appropriate
                - Explain concepts briefly and practically
            4. Tags
                - 3~5 single-word tags
            
            Format exactly as:
            
            # Title
            
            ## Summary
            ...
            
            ## Body
            ...
            
            ## Tags
            - tag1
            - tag2
            - tag3
            """.formatted(this.promptText);

        return this;
    }
}
