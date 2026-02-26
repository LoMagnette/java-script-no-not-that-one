import java.io.File;
import java.util.Arrays;

import files.FileMetadata;
import files.FilePrinterService;

static String DEFAULT_PATH = "./";

void main() {

    var path = IO.readln("Please enter the path you want to use to list file (ie: /bin/):");

    if(path == null || path.isEmpty())
        path = DEFAULT_PATH;

    var dir = new File(path);
    if (!dir.exists() || !dir.isDirectory()) {
        IO.println("The path you entered is not a valid directory.");
        return;
    }
    var files = Arrays.stream(dir.listFiles()).map(FileMetadata::fromFile).toList();
    new FilePrinterService().prettyPrintFiles(files);
}