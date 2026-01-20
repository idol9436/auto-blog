package com.sangsang.autoblog.application.service;

import org.springframework.stereotype.Service;

import com.sangsang.autoblog.application.command.PromptCommand;
import com.sangsang.autoblog.domain.model.PostContent;
import com.sangsang.autoblog.domain.model.Prompt;

import com.sangsang.autoblog.domain.port.in.PromptUseCase;
import com.sangsang.autoblog.domain.port.out.PromptApiPort;

@Service
public class PromptService implements PromptUseCase{

    private final PromptApiPort promptPort;
    public PromptService(PromptApiPort promptPort) {
        this.promptPort = promptPort;
    }

    @Override
    public PostContent getPostContent(PromptCommand promptCmd) {
        
        PostContent content = null;
        
        try {
            Prompt prompt = Prompt.from(promptCmd);
            String title = promptPort.genTextByPrompt(prompt.parseToTitle());
            String body = promptPort.genTextByPrompt(prompt.parseToBody());
            String[] tags = promptPort.genTextByPrompt(prompt.parseToTags()).split(",");
            String summary = promptPort.genTextByPrompt(prompt.parseToSummary());
            String imageUrl = promptPort.genTextByPrompt(prompt.parseToImageUrl());

            content = PostContent.postContentFrom(title, body, tags, imageUrl, summary);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    @Override
    public PostContent getMarkdownContent(PromptCommand promptCmd){
        
        PostContent content = null;
        
        try {
            Prompt prompt = Prompt.from(promptCmd);
            String mdText = promptPort.genTextByPrompt(prompt.parseToMarkdownText());
            content = PostContent.markdownContentFrom(mdText);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    @Override
    public PostContent getConfirmedMDContent(PromptCommand promptCmd) {
        PostContent content = null;
        
        try {
            Prompt prompt = Prompt.from(promptCmd);
            String mdText = promptPort.genTextByPrompt(prompt.parseToConfirmMD());
            
            if(mdText.length() < 10 && mdText.toUpperCase().contains("CONFIRMED")){
                System.out.println("isConfirmed : " + mdText);
                content = PostContent.markdownContentFrom(promptCmd.promptText());
            } else {
                content = PostContent.markdownContentFrom(mdText);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }
}
