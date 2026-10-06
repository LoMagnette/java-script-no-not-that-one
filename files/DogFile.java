package files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * A file the dog fetched: its path and the lines it carried back.
 *
 * <p>Part of the JEP 458 (multi-file) demo — this sibling source is compiled
 * on demand by {@code java DogAdvanced.java}, no build tool required.
 */
public record DogFile(String name, List<String> lines) {

    public static Optional<DogFile> fromPath(Path path){
        if(!Files.isRegularFile(path))
            return Optional.empty();
        try {
            return Optional.of(new DogFile(path.toString(), Files.readAllLines(path)));
        } catch (IOException e) {
            System.err.println("Error reading file: " + path);
            return Optional.empty();
        }
    }
}
