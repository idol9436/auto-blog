package com.sangsang.autoblog.adapter.in.web.controller.preview;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sangsang.autoblog.adapter.in.web.dto.PromptRequestDTO;
import com.sangsang.autoblog.adapter.in.web.dto.PromptResponseDTO;
import com.sangsang.autoblog.domain.model.PostContent;
import com.sangsang.autoblog.domain.port.in.PromptUseCase;


@Controller
@RequestMapping("/content")
public class ContentController {

    private final PromptUseCase promptUseCase;

    public ContentController(PromptUseCase promptUseCase) {
        this.promptUseCase = promptUseCase;
    }

    @GetMapping("/preview")
    public ModelAndView getSamplePreview() {
        ModelAndView mav = new ModelAndView();

        PostContent content = new PostContent(); // Sample content for preview    

        mav.addObject("content", content);
        mav.setViewName("pages/preview/content-post");
        return mav;
    }
    
    
    @PostMapping("/prompt")
    public ModelAndView getMarkdownContent(PromptRequestDTO promptRequestDTO) {
        ModelAndView mav = new ModelAndView();

        PostContent content = promptUseCase.getMarkdownContent(promptRequestDTO.toCommand());

        mav.addObject("content", PromptResponseDTO.from(content));
        mav.setViewName("pages/preview/content-markdown");
        return mav;
    }
    
    // TODO : 예비
    @PostMapping("/prompt/post")
    public ModelAndView getPostContent(PromptRequestDTO promptRequestDTO) {
        ModelAndView mav = new ModelAndView();

        PostContent content = promptUseCase.getPostContent(promptRequestDTO.toCommand());

        mav.addObject("content", PromptResponseDTO.from(content));
        mav.setViewName("pages/preview/content-post");
        return mav;
    }
    
}
