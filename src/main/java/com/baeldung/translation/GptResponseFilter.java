package com.baeldung.translation;

import org.springframework.stereotype.Component;

@Component
public class GptResponseFilter {

    public String filter(String input) {
        return input.replace("\"", "");
    }
}
