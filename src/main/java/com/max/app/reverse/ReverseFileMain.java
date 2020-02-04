package com.max.app.reverse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ReverseFileMain {

    private static final Logger LOG = LoggerFactory.getLogger(ReverseFileMain.class);

    public static void main(String[] args) throws Exception {

        failIfWrongArgumentsCount(args);

        Path src = Paths.get(args[0]);
        Path dest = Paths.get(args[1]);

        failIfFileDoesntExist(src);

        Files.deleteIfExists(dest);
        Files.createFile(dest);

        ReverseFileUtil.copyContentInReverseOrder(src, dest);

        LOG.info("Main done. java version {}", System.getProperty("java.version"));
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

}
