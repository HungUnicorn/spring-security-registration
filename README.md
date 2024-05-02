## Generate translations by Spring AI for Login and Registration Example Project with Spring Security
This project takes the application provided by Baeldung and can generate the translated messages displaying to the users.
So this application can serve the users other than the English users.

There are 99 messages stored in [messages_en.properties](https://github.com/HungUnicorn/spring-security-registration/blob/master/src/main/resources/messages_en.properties) for the Thymeleaf frontend to display to the users.
Running  `TranslationApplication` class in [com.baeldung.translation](https://github.com/HungUnicorn/spring-security-registration/tree/master/src/main/java/com/baeldung/translation) folder can generate the translations.

Environment variables
- `HTTP_ACCEPT_LANGUAGE`=
- `SPRING_AI_OPENAI_API-KEY`=

`HTTP_ACCEPT_LANGUAGE` accepts a string, and the prompt decides the best fitted language.
For example, setting `HTTP_ACCEPT_LANGUAGE=de`, the AI agent knows it needs to translate the text into German.
The example translation output is stored in [messages_de.properties](https://github.com/HungUnicorn/spring-security-registration/blob/master/src/main/resources/messages_de.properties).


### Build and Deploy the Project
```
mvn clean install
```

This is a Spring Boot project, so you can deploy it by simply using the main class: `Application.java`

Once deployed, you can access the app at: 

https://localhost:8081
