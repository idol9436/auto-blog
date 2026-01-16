package com.sangsang.autoblog.domain.port.in;

import com.sangsang.autoblog.application.command.PromptCommand;
import com.sangsang.autoblog.domain.model.PostContent;

public interface PromptUseCase {
    PostContent getPostContent(PromptCommand prompt);
    PostContent getMarkdownContent(PromptCommand prompt);
}