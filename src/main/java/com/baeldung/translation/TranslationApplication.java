package com.baeldung.translation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class TranslationApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplicationBuilder(TranslationApplication.class)
                .web(WebApplicationType.NONE)
                .build();

        ConfigurableApplicationContext ctx = app.run(args);

        try (ctx) {
            MessagesTranslator translator = ctx.getBean(MessagesTranslator.class);
            translator.translateProperties();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
