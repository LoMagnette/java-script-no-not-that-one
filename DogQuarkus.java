/// usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21+
//JAVA_OPTIONS --add-opens java.base/java.lang=ALL-UNNAMED
//DEPS io.quarkus.platform:quarkus-bom:3.38.2@pom
//DEPS io.quarkus:quarkus-picocli
//DEPS io.quarkus:quarkus-rest-client-jackson

import io.quarkus.picocli.runtime.annotations.TopCommand;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;


@TopCommand
@Command(name = "dog", mixinStandardHelpOptions = true, version = "dog 1.0",
        description = "dog on Quarkus - like cat, but a good boy that also fetches from the web")
public class DogQuarkus implements Callable<Integer> {

    @Inject
    DogService dog;                           // CDI-managed bean, not `new`

    @Parameters(arity = "0..1", description = "the file to fetch")
    java.nio.file.Path file;

    @Option(names = {"-n", "--number"}, description = "number the output lines")
    boolean number;

    @Option(names = {"-f", "--fetch"}, description = "fetch a random good boy from the web")
    boolean fetch;

    @Override
    public Integer call() throws Exception {
        if (file == null && !fetch) {
            System.out.println("Give the dog a file to fetch, or --fetch a good boy.");
            return 0;
        }
        int exit = dog.print(file, number);
        if (fetch) {
            System.out.println("🦴 here is a good boy: " + dog.goodBoy());
        }
        return exit;
    }
}

@ApplicationScoped
class DogService {

    @Inject
    @ConfigProperty(name = "dog.greeting", defaultValue = "🐕  fetching")
    String greeting;                          // type-safe config, no boilerplate

    @Inject
    @RestClient
    DogApi api;                               // declarative HTTP + JSON, no HttpClient

    String goodBoy() {
        return api.random().message();        // one line — the extension does the rest
    }

    int print(java.nio.file.Path file, boolean number) throws IOException {
        if (file == null) {
            return 0;
        }
        if (!Files.isRegularFile(file)) {
            System.out.println("Bad dog! Not a readable file: " + file);
            return 1;
        }
        System.out.println(greeting + " " + file);
        var lines = Files.readAllLines(file);
        for (int i = 0; i < lines.size(); i++) {
            System.out.println(number
                    ? String.format("%6d  %s", i + 1, lines.get(i))
                    : lines.get(i));
        }
        return 0;
    }
}


@RegisterRestClient(baseUri = "https://dog.ceo/api")
interface DogApi {

    @GET
    @Path("/breeds/image/random")
    DogImage random();
}

record DogImage(String message, String status) {
}
