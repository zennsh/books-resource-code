package chapter1.zipfile;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by ZHEN on 4/25/2016.
 */
public class ZipFileTest {
    public static void main(String[] args) throws IOException {
        Path dir = Paths.get("C:/Users/502396513/Downloads");
        try (DirectoryStream<Path> entities = Files.newDirectoryStream(dir, "Chart.zip")) {
            for (Path entry : entities) {
                System.out.println(entry.getFileName());
                System.out.println(entry.toString());
                FileSystem fs = FileSystems.newFileSystem(entry, null);
//                Files.copy(fs.getPath("Chart.js"), Paths.get("C:/Users/502396513/Downloads/111/test"));
                Files.walkFileTree(fs.getPath("/"), new SimpleFileVisitor<Path>() {
                    @Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        System.out.println(file);
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                if (attrs.isDirectory()) {
                    System.out.println(path);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
			public FileVisitResult visitFileFailed(Path path, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
