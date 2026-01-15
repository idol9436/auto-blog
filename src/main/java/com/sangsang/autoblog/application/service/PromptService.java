package com.sangsang.autoblog.application.service;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

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
    public PostContent getAutoContent(Prompt prompt) {
        
        PostContent content = null;
        
        try {
            String mdText = promptPort.genTextByPrompt(prompt.parsingToMarkdownText());
            // Parser parser = Parser.builder().build();
            // HtmlRenderer renderer = HtmlRenderer.builder().build();

            // String markdown = post.getMarkdown();
            // String html = renderer.render(parser.parse(markdown));

            // String title = promptPort.genTextByPrompt(prompt.parsingToTitle());
            // String body = promptPort.genTextByPrompt(prompt.parsingToBody());
            // String[] tags = {""};
            // String summary = "";
            // String imageUrl = "";
            // String[] tags = promptPort.genTextByPrompt(prompt.parsingToTags()).split(",");
            // String summary = promptPort.genTextByPrompt(prompt.parsingToSummary());
            // String imageUrl = promptPort.genTextByPrompt(prompt.parsingToImageUrl());

            // content = new PostContent(title, body, tags, imageUrl, summary);
            System.out.println(mdText);
            System.out.println("==================================================");
            content = new PostContent(this.parseMarkdownToHTML(mdText));
            System.out.println(this.parseMarkdownToHTML(mdText));
            
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
