package files;

import java.io.File;

public record FileMetadata(String name, long size, boolean isDirectory) {

    public static FileMetadata fromFile(File file){
        return new FileMetadata(file.getName(), file.length(), file.isDirectory());
    }
}
