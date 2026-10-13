///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.aesh:aesh:3.18.3
//DEPS org.aesh:aesh-processor:3.18.3
//JAVAC_OPTIONS -proc:full
//JAVA_OPTIONS --enable-native-access=ALL-UNNAMED

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.aesh.AeshConsoleRunner;
import org.aesh.command.Command;
import org.aesh.command.CommandDefinition;
import org.aesh.command.CommandResult;
import org.aesh.command.invocation.CommandInvocation;
import org.aesh.command.option.Argument;
import org.aesh.command.option.Option;

@CommandDefinition(name = "dog", description = "fetch a file's contents, dog-style", generateHelp = true)
public class DogAesh implements Command<CommandInvocation> {

    @Option(shortName = 'n', name = "number", hasValue = false,
            description = "number the output lines")
    boolean numbers;

    @Argument(description = "the file to fetch")
    String file;

    public static void main(String... args) {
        AeshConsoleRunner.builder()
                .command(DogAesh.class)
                .prompt("[dog]$ ")
                .addExitCommand()
                .start();
    }

    @Override
    public CommandResult execute(CommandInvocation inv) {
        var path = Path.of(file);
        if (!Files.isRegularFile(path)) {
            IO.println("🐕 can't fetch " + file + ": no such file");
            return CommandResult.FAILURE;
        }
        printFile(path);
        return CommandResult.SUCCESS;
    }

    private void printFile(Path path) {
        IO.println("🐕 " + path);
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toString()))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                if (numbers) {
                    IO.print("\t" + lineNumber);
                }
                IO.println("\t" + line);
                lineNumber++;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
