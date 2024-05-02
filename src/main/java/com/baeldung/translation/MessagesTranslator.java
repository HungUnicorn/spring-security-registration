package com.baeldung.translation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Service
public class MessagesTranslator {

    private final GptClient gptClient;

    private final GptResponseFilter filter;

    @Value("classpath:messages_en.properties")
    private Resource messagesEn;

    public MessagesTranslator(GptClient gptClient, GptResponseFilter filter) {
        this.gptClient = gptClient;
        this.filter = filter;
    }

    public void translateProperties() throws IOException {
        String resourceFolder = "src/main/resources";
        String httpAcceptLanguageHeader = System.getenv("HTTP_ACCEPT_LANGUAGE");

        Properties englishProps = new Properties();
        englishProps.load(messagesEn.getInputStream());

        String translatedFilename = resourceFolder + "/messages_" + httpAcceptLanguageHeader + ".properties";

        Properties translatedProps = new Properties();

        try (InputStream input = new FileInputStream(translatedFilename)) {
            translatedProps.load(input);
        } catch (IOException e) {
            // File doesn't exist or cannot be read, continue without loading
        }

        for (String key : englishProps.stringPropertyNames()) {
            if (translatedProps.containsKey(key)) {
                continue; // Skip translation if key already exists
            }

            String englishText = englishProps.getProperty(key);
            String translatedText = gptClient.generateTranslation(englishText, httpAcceptLanguageHeader);
            String filteredText = filter.filter(translatedText);
            translatedProps.setProperty(key, filteredText);
        }

        storePropertiesWithoutEscaping(englishProps, translatedProps, translatedFilename);
    }

    public void storePropertiesWithoutEscaping(Properties engProps, Properties translatedProps, String filename) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename), StandardCharsets.ISO_8859_1))) {
            for (String key : engProps.stringPropertyNames()) {
                String value = translatedProps.getProperty(key);
                writer.write(key + "=" + value + "\n");
            }
        }
    }
}
