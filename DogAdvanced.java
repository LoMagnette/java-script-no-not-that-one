import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import files.*;


private static boolean lineNumer = true;

void main() {
    var input = IO.readln("Which file(s) should the dog fetch? (space-separated):");
    var files = List.of(input.trim().split("\\s+")).stream()
            .map(Path::of)
            .map(DogFile::fromPath)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();

    new DogPrinterService(lineNumer).fetch(files);
}
