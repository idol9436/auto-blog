package com.sangsang.autoblog.application.service;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
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

            content = new PostContent(title, body, tags, imageUrl, summary);
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
            content = new PostContent(this.parseMarkdownToHTML(mdText));
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    public String parseMarkdownToHTML(String mdText){
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(parser.parse(mdText));
    }
}
