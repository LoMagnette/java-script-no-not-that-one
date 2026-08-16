/// usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS info.picocli:picocli:4.6.3


import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.List;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.concurrent.Callable;

@Command(name = "DogCli", mixinStandardHelpOptions = true, version = "DogCli 0.1",
        description = "DogCli made with jbang")
class DogCli implements Callable<Integer> {

    @Parameters(index = "0", description = "The greeting to print", defaultValue = "World!", split=",")
    private List<String> files;

    @CommandLine.Option(names = {"-n", "--numbers"}, description = "display line numbers", defaultValue = "false")
    private boolean numbers;

    public static void main(String... args) {
        int exitCode = new CommandLine(new DogCli()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception { // your business logic goes here...

        files.stream()
                .map(Path::of)
                .filter(Files::isRegularFile)
                .forEach(f -> printFile(f));
        return 0;

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
