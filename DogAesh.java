/// usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.aesh:aesh:3.16.8
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.aesh.AeshConsoleRunner;
import org.aesh.command.Command;
import org.aesh.command.CommandDefinition;
import org.aesh.command.CommandResult;
import org.aesh.command.invocation.CommandInvocation;
import org.aesh.command.option.Option;
import org.aesh.command.option.Argument;


@CommandDefinition(name = "dog", description = "fetch a file's contents, dog-style")
public class DogAesh implements Command<CommandInvocation> {

    @Option(shortName = 'n', name = "number", hasValue = false,
            description = "number the output lines")
    private boolean numbers;

    @Argument(description = "the file to fetch")
    private String file;

    public static void main(String... args) {
        AeshConsoleRunner.builder()
                .command(DogAesh.class)
                .prompt("[dog]$ ")
                .addExitCommand()
                .start();
    }

    @Override
    public CommandResult execute(CommandInvocation inv){// your business logic goes here...
        var path = Path.of(file);
        if(!Files.isRegularFile(path))
            return CommandResult.FAILURE;
        printFile(path);
        return CommandResult.SUCCESS;
    }

    private void printFile(Path path) {
        IO.println("🐕 "+path.toString());
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toString()))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                if(numbers){
                    IO.print("\t"+lineNumber);
                }
                IO.println("\t" + line);
                lineNumber++;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
