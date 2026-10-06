package files;

import java.util.List;

/**
 * Prints the files the dog fetched, dog-style: a 🐕 header per file, optional
 * line numbers, and a cheerful {@code woof! 🐾} at the end.
 */
public class DogPrinterService {

    private static final String BLUE = "[34m";
    private static final String YELLOW = "[33m";
    private static final String RESET = "[0m";

    private final boolean numberLines;

    public DogPrinterService(boolean numberLines) {
        this.numberLines = numberLines;
    }

    public void fetch(List<DogFile> files) {
        files.forEach(this::print);
        System.out.println("woof! 🐾");
    }

    private void print(DogFile file) {
        System.out.printf("%s🐕  %s%s%n", BLUE, file.name(), RESET);
        var lines = file.lines();
        for (int i = 0; i < lines.size(); i++) {
            if (numberLines) {
                System.out.printf("%s%6d%s  %s%n", YELLOW, i + 1, RESET, lines.get(i));
            } else {
                System.out.println(lines.get(i));
            }
        }
    }
}
