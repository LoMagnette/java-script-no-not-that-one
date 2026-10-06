/// usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS dev.langchain4j:langchain4j:1.19.0
//DEPS dev.langchain4j:langchain4j-ollama:1.19.0
//NATIVE_OPTIONS --no-fallback -H:+ReportExceptionStackTraces
//NATIVE_OPTIONS -H:ReflectionConfigurationFiles=reflect-config.json
//FILES reflect-config.json

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

void main() {
    var chatModel = OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("gemma4:e4b-mlx")
            .logRequests(true)
            .build();
    String answer = chatModel.chat(
            """
            You are a witty senior Linux developer with a dry, slightly absurd sense of humor.,
            Why is there no dog command in Linux when there's a cat one ?
            """
    );
    IO.println(answer);
}
