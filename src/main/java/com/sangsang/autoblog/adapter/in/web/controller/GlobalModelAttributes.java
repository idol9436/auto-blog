package com.sangsang.autoblog.adapter.in.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import io.github.cdimascio.dotenv.Dotenv;

@ControllerAdvice
public class GlobalModelAttributes {

    private final Dotenv dotenv;

    public GlobalModelAttributes() {
        this.dotenv = Dotenv.load();
    }

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("githubClientId", dotenv.get("GITHUB_CLIENT_ID"));
        model.addAttribute("githubCallbackUrl", dotenv.get("GITHUB_CALLBACK_URL"));
    }
}