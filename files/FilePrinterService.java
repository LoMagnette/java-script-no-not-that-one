package files;
import java.text.DecimalFormat;
import java.util.List;


public class FilePrinterService {

    public void prettyPrintFiles(List<FileMetadata> files) {
        files.forEach(FilePrinterService::printEntry);
    }

    private static void printEntry(FileMetadata file) {
        
        var display =file.isDirectory() ? "\u001B[34m" + file.name() + "\u001B[0m" : file.name();

        System.out.printf(
                "- %-12s %s%n",
                size(file),
                display
        );
    }

    private static String size(FileMetadata file) {
        if (file.isDirectory())
            return "-";
        long bytes = file.size();
        if (bytes == 0)
            return "0 B";

        var units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int group = (int) (Math.log(bytes) / Math.log(1024));
        return new DecimalFormat("#,##0.#").format(bytes / Math.pow(1024, group)) + " " + units[group];
    }
}
