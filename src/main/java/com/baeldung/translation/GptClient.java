package com.baeldung.translation;

import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class GptClient {
    BeanOutputParser<String> stringBeanOutputParser = new BeanOutputParser<>(String.class);

    final String promptString = """
            You are given a translator task. Only output the translation.
            The language parameter "{language}" is defined by HTTP Header Accept-Language.                      \s
            Translate the text to the given language parameter: "{text}"
    """;

    private final AiClient aiClient;

    public GptClient(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    public String generateTranslation(String input, String language) {
        PromptTemplate promptTemplate = new PromptTemplate(promptString);
        promptTemplate.add("text", input);
        promptTemplate.add("language", language);

        promptTemplate.setOutputParser(stringBeanOutputParser);

        AiResponse response = aiClient.generate(promptTemplate.create());
        return response.getGeneration().getText();
    }
}
