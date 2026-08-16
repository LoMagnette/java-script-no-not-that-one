import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Dog {
    public static void main(String[] args) {
        System.out.println("Which file(s) should the dog fetch? (space-separated):");
        var input = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            input = reader.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        List.of(input.trim().split("\\s+")).stream()
                .map(Path::of)
                .filter(Files::isRegularFile)
                .forEach(Dog::printFile);

    }

    private static void printFile(Path path){
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toString()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
