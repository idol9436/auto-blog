package com.sangsang.autoblog.adapter.out.api;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.sangsang.autoblog.domain.model.Prompt;
import com.sangsang.autoblog.domain.port.out.PromptApiPort;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class GeminiClient implements PromptApiPort {

    private final Dotenv dotenv;
    private final String API_KEY;

    public GeminiClient() {
        this.dotenv = Dotenv.load();
        this.API_KEY = dotenv.get("GEMINI_API_KEY");
    }

    // Handler method
    public String genTextByPrompt(Prompt prompt) throws Exception {

        String result = "";
        if (prompt.promptFiles == null || prompt.promptFiles.length == 0) {
            result = getTextByOnlyTextPrompt(prompt.promptText);
            return result;
        } else {
            result = getTextByPromptWithImage(prompt);
        }

        return result;
    }


    private String getTextByOnlyTextPrompt(String promptText) {
        String result = "";
        Client client = Client.builder().apiKey(API_KEY).build();

        try (client){
            GenerateContentResponse response =
                client.models.generateContent(
                    "gemini-2.5-flash",
                    promptText,
                    null);

            result = response.text(); 
         
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getTextByPromptWithImage(Prompt prompt) throws Exception {

        String result = "";
        Client client = Client.builder().apiKey(API_KEY).build();

        String path = prompt.promptFiles[0].getOriginalFilename();
        byte[] imageData = Files.readAllBytes(Paths.get(path));

        Content content =
        Content.fromParts(
                Part.fromText(prompt.promptText),
                Part.fromBytes(imageData, "image/jpeg")
            );

        try (client){
            GenerateContentResponse response =
                client.models.generateContent(
                    "gemini-2.5-flash",
                    content,
                    null);

            result = response.text(); 
         
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

 
}