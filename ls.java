///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS info.picocli:picocli:4.6.3

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Callable;

@Command(name = "ls", mixinStandardHelpOptions = true, version = "ls 0.1", description = "ls made with jbang")
class ls implements Callable<Integer> {

    @Parameters(index = "0", description = "path")
    private String path;

    @Option(names = { "-s","--sorted" }, description="sort the result")
    private boolean sorted;

    public static void main(String... args) {
        int exitCode = new CommandLine(new ls()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            IO.println("The path you entered is not a valid directory.");
            return -1;
        }
        var files = Arrays.stream(dir.list());
        if (sorted) {
            files = files.sorted();
        }
        files.forEach(System.out::println);
        return 0;
    }
}
