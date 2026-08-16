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

public class DogAvanced {

    private static boolean lineNumer = true;

    public static void main(String[] args) {
        System.out.println("Which file(s) should the dog fetch? (space-separated):");
        var input = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            input = reader.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        var files = List.of(input.trim().split("\\s+")).stream()
                .map(Path::of)
                .map(DogFile::fromPath)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        new DogPrinterService(lineNumer).fetch(files);

    }
}
