import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ListFiles {

    public static void main(String[] args) {

        System.out.println("Please enter the path you want to use to list file (ie: /bin/):");
        var path = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            path = reader.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // list files in a directory
        var dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("The path you entered is not a valid directory.");
            return;
        }
        Arrays.stream(dir.listFiles()).forEach(e -> System.out.println(e.getName()));
    }
}