package com.sangsang.autoblog.domain.model;

import org.springframework.web.multipart.MultipartFile;

import com.sangsang.autoblog.application.command.PromptCommand;

public class Prompt {

    public String promptText;
    public final MultipartFile[] promptFiles;

    private Prompt(String promptText, MultipartFile[] promptFiles) {
        this.promptText = promptText;
        this.promptFiles = promptFiles;
    }

    public static Prompt from(PromptCommand cmd){
        return new Prompt(cmd.promptText(), cmd.promptFiles());
    }

    public Prompt parseToTitle(){
        this.promptText = "Generate a blog title in a few words for the following prompt [%s] ".formatted(promptText);
        return this;
    }

    public Prompt parseToBody(){
        this.promptText = "Generate a detailed blog body in a 2000 bytes for the following prompt [%s] ".formatted(promptText);
        return this;
    }

    public Prompt parseToTags(){
        this.promptText = "Generate relevant tags with comma separated for the following blog prompt [%s] ".formatted(promptText);
        return this;
    }   

    public Prompt parseToSummary(){
        this.promptText = "Generate a concise summary in a 200 bytes for the following blog prompt [%s] ".formatted(promptText);
        return this;
    }

    public Prompt parseToImageUrl(){
        this.promptText = "Generate an image description use freeSite for the following blog prompt [%s] ".formatted(promptText);
        return this;
    }

    public Prompt parseToMarkdownText(){
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

    public Prompt parseToConfirmMD(){

        this.promptText ="""
        아래 마크다운 문구는 블로그 게시 후보입니다.
        원문은 그대로 유지하고, 문제가 있는 부분에만 주석으로 지적하세요.

        검토 기준:
        - 문맥상 어색한 표현
        - 사실 오류 또는 과도한 일반화
        - 톤 불일치
        - 마크다운 문법 오류

        주석 규칙:
        - 원문 텍스트는 절대 수정하지 말 것
        - 문제 있는 문장 바로 아래에 주석 추가
        - 주석 형식: <!-- ISSUE: 지적 내용 -->

        문제가 없다면 "CONFIRMED" 만 출력하세요.

        [검토 대상]
        %s
        """.formatted(promptText);

        return this;
    }
}
