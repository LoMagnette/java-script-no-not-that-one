///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS dev.langchain4j:langchain4j:1.11.0
//DEPS dev.langchain4j:langchain4j-ollama:1.11.0
//NATIVE_OPTIONS --no-fallback -H:+ReportExceptionStackTraces
//NATIVE_OPTIONS -H:ReflectionConfigurationFiles=reflect-config.json
//FILES reflect-config.json

import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.chat.ChatModel;

void main(){
    ChatModel chatModel = OllamaChatModel.builder()
    .baseUrl("http://localhost:11434")
    .modelName("llama3.2:latest")
    .logRequests(true)
    .build();

    String answer = chatModel.chat("Provide 3 short bullet points explaining why Java is awesome for writing scripts");
    System.out.println(answer);
}
