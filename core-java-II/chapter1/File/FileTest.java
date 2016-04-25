package chapter1.File;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by 502396513 on 4/25/2016.
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
        Path dir = Paths.get("C:/Users/502396513/Downloads");
        try (DirectoryStream<Path> entities = Files.newDirectoryStream(dir, "**.msi")) {
            for (Path entry : entities) {
                System.out.println(entry.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                if (attrs.isDirectory()) {
                    System.out.println(path);
                }
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult visitFileFailed(Path path, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
