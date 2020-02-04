package com.max.app.reverse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

final class ReverseFileUtil {

    // use 12 KB as a buffer
    private static final int BUF_SIZE = 12 * 1024;

    private ReverseFileUtil() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    static void copyContentInReverseOrder(Path src, Path dest) throws IOException {

        assert src != null : "null 'src' detected";
        assert dest != null : "null 'dest' detected";

        final byte[] buf = new byte[calculateBufSize(src)];

        assert buf.length > 0 : "0 'buf.length' detected";

        long offset = Files.size(src);

        try (RandomAccessFile srcFile = new RandomAccessFile(src.toFile(), "r");
             OutputStream out = Files.newOutputStream(dest)) {

            while (offset != 0) {

                long oldOffset = offset;
                offset = Math.max(0, offset - BUF_SIZE);

                srcFile.seek(offset);

                int bytesToReadCount = (int) (oldOffset - offset);
                int readBytes = srcFile.read(buf, 0, bytesToReadCount);

                reverseInPlace(buf, readBytes);

                out.write(buf, 0, readBytes);
            }
        }
    }

    private static int calculateBufSize(Path path) throws IOException {
        long bufSize = Files.size(path);

        if (bufSize >= BUF_SIZE) {
            return BUF_SIZE;
        }

        // bufSize < BUF_SIZE, so it's safe to convert to int

        return nextPowerOfTwo((int)bufSize);
    }

    private static int nextPowerOfTwo(int value){
        --value;

        value |= value >> 1;
        value |= value >> 2;
        value |= value >> 4;
        value |= value >> 8;
        value |= value >> 16;;

        ++value;

        return value;
    }

    private static void reverseInPlace(byte[] buf, int size) {

        assert size <= buf.length : "size > buf.length, size = " + size + ", buf.length = " + buf.length;

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
