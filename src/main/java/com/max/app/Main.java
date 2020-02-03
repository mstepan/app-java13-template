package com.max.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static final int BUF_SIZE = 13;

    /**
     * Reverse file content.
     */
    public static void main(String[] args) throws Exception {

        failIfWrongArgumentsCount(args);

        Path src = Paths.get(args[0]);
        Path dest = Paths.get(args[1]);

        failIfFileDoesntExist(src);

        Files.deleteIfExists(dest);
        Files.createFile(dest);

        copyContentInReverseOrder(src, dest);

        LOG.info("Main done. java version {}", System.getProperty("java.version"));
    }

    private static void copyContentInReverseOrder(Path src, Path dest) throws IOException {
        final byte[] buf = new byte[BUF_SIZE];

        long offset = Files.size(src);

        try (RandomAccessFile srcFile = new RandomAccessFile(src.toFile(), "r");
             OutputStream out = Files.newOutputStream(dest)) {

            while (offset != 0) {

                long oldOffset = offset;
                offset = Math.max(0, offset - BUF_SIZE);

                srcFile.seek(offset);

                int bytesToReadCount = (int) (oldOffset - offset);
                int readBytes = srcFile.read(buf, 0, bytesToReadCount);

//                String str = new String(toCharArray(buf, readBytes));

                reverseInPlace(buf, readBytes);

//                String reverted = new String(toCharArray(buf, readBytes));

                out.write(buf, 0, readBytes);
            }
        }
    }

    private static void failIfWrongArgumentsCount(String[] args) {
        assert args != null : "null 'args' array passed";
        if (args.length != 2) {
            throw new IllegalArgumentException("Specify 2 parameters");
        }
    }

    private static void failIfFileDoesntExist(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalStateException("File for reverse doesn't exist" + path.toAbsolutePath().toString());
        }
    }

    private static char[] toCharArray(byte[] data, int readBytes) {
        char[] res = new char[readBytes];

        for (int i = 0; i < readBytes; ++i) {
            res[i] = (char) data[i];
        }
        return res;
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String toHexString(byte[] data) {
        StringBuilder res = new StringBuilder(data.length);

        for (byte singleByte : data) {

            int normalized = singleByte & 0xFF;

            res.append(HEX_DIGITS[normalized >> 4]);
            res.append(HEX_DIGITS[normalized & 0x0F]);
        }
        return res.toString();
    }

    private static void reverseInPlace(byte[] buf, int size) {

        assert size <= buf.length  : "size > buf.length, size = " + size + ", buf.length = " + buf.length;

        int from = 0;
        int to = size - 1;

        while (from < to) {
            byte singleByte = buf[from];

            buf[from] = buf[to];
            buf[to] = singleByte;
            ++from;
            --to;
        }
    }

}
