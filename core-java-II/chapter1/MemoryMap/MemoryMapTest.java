package chapter1.MemoryMap;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;

/**
 * This program computes the CRC chacksum of a file in four ways.<br>
 * Created by ZHEN on 4/29/2016.
 */
public class MemoryMapTest {

    public static long checksumInputStream(Path path) {
        CRC32 crc = new CRC32();
        try (InputStream in = Files.newInputStream(path)) {

            int c;
            while ((c = in.read()) != -1) {
                crc.update(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc.getValue();
    }

    public static long checksumBufferedInputStream(Path path) {
        CRC32 crc = new CRC32();
        try (InputStream in = new BufferedInputStream(Files.newInputStream(path))) {
            int c;
            while ((c = in.read()) != -1) {
                crc.update(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc.getValue();
    }

    public static long checksumRandomAccessFile(Path path) {
        CRC32 crc = new CRC32();
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")) {
            long length = file.length();
            int c;
            for (int i = 0; i < length; i++) {
                file.seek(i);
                c = file.readByte();
                crc.update(c);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc.getValue();
    }

    public static long checksumMappedFile(Path path) {
        CRC32 crc = new CRC32();
        try (FileChannel channel = FileChannel.open(path)) {

            long length = channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);

            int c;
            //  Ordered Iterate
//            while (buffer.hasRemaining()) {
//                c = buffer.get();
//                crc.update(c);
//            }
            //  Random access support
            for (int i = 0; i < length; i++) {
                c = buffer.get(i);
                crc.update(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return crc.getValue();
    }

    public static void main(String[] args) {
        System.out.println("Input Steam");
        long start = System.currentTimeMillis();
        Path path = Paths.get("C:/Users/502396513/Downloads/atrecply.msi");
        long crcValue = checksumInputStream(path);
        System.out.println(Long.toHexString(crcValue));
        System.out.println(System.currentTimeMillis() - start + " Milliseconds");

        System.out.println("Buffered input Steam");
        start = System.currentTimeMillis();
        crcValue = checksumBufferedInputStream(path);
        System.out.println(Long.toHexString(crcValue));
        System.out.println(System.currentTimeMillis() - start + " Milliseconds");

        System.out.println("Random Access File");
        start = System.currentTimeMillis();
        crcValue = checksumRandomAccessFile(path);
        System.out.println(Long.toHexString(crcValue));
        System.out.println(System.currentTimeMillis() - start + " Milliseconds");

        System.out.println("Mapped File");
        start = System.currentTimeMillis();
        crcValue = checksumMappedFile(path);
        System.out.println(Long.toHexString(crcValue));
        System.out.println(System.currentTimeMillis() - start + " Milliseconds");
    }
}
