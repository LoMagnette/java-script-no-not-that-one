/// usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS dev.langchain4j:langchain4j:1.19.0
//DEPS dev.langchain4j:langchain4j-ollama:1.19.0
//JAVA 25+
//RUNTIME_OPTIONS --add-modules jdk.incubator.vector --enable-native-access=ALL-UNNAMED
//DEPS com.qxotic:jinfer-bom:0.3.1@pom
//DEPS com.qxotic:jinfer-langchain4j com.qxotic:jinfer-models-all
//DEPS com.qxotic:jam-native com.qxotic:jam-vector
//DEPS org.slf4j:slf4j-nop:2.0.17
//NATIVE_OPTIONS --no-fallback -H:+ReportExceptionStackTraces
//NATIVE_OPTIONS -H:ReflectionConfigurationFiles=reflect-config.json
//FILES reflect-config.json

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import com.qxotic.jinfer.langchain4j.JinferChatModel;

void main() {
    var chatModel = JinferChatModel.builder()
            .modelPath(Path.of("./gemma-4-E2B-it-Q8_0.gguf"))
            .modelName("gemma4:e2b")
            .build();
    String answer = chatModel.chat(
            """
            You are a witty senior Linux developer with a dry, slightly absurd sense of humor.,
            Why is there no dog command in Linux when there's a cat one ?
            """
    );
    IO.println(answer);
}
