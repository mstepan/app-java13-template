package com.max.app.cleaners;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.Cleaner;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

final class FileCleanerMain {


    private static final class FileLinesIterator implements AutoCloseable {

        private static final Cleaner CLEANER_REGISTRY = Cleaner.create();

        private final RandomAccessFile file;

        private final Cleaner.Cleanable cleanable;
        private final AtomicBoolean closed = new AtomicBoolean(false);

        FileLinesIterator(File filePath) {
            try {
                file = new RandomAccessFile(filePath, "r");
                cleanable = CLEANER_REGISTRY.register(this, new State(file, closed));
            }
            catch (FileNotFoundException fileNotFoundEx) {
                throw new IllegalArgumentException(fileNotFoundEx);
            }
        }

        String nextLine() {
            if (closed.get()) {
                throw new IllegalStateException("File already closed");
            }

            try {
                return file.readLine();
            }
            catch (IOException ioEx) {
                throw new IllegalStateException(ioEx);
            }
        }


        @Override
        public void close() throws Exception {
            // below function can be called from 'main' thread or 'Cleaner-0' thread.
            cleanable.clean();
        }

        private static final class State implements Runnable {

            private final RandomAccessFile fileRef;
            private final AtomicBoolean closedStatus;

            State(RandomAccessFile fileRef, AtomicBoolean closedStatus) {
                this.fileRef = fileRef;
                this.closedStatus = closedStatus;
            }

            @Override
            public void run() {
                try {
                    fileRef.close();
                    closedStatus.set(true);
                    System.out.printf("File closed from thread '%s' %n", Thread.currentThread().getName());
                }
                catch (IOException ioEx) {
                    throw new IllegalStateException(ioEx);
                }
            }
        }
    }

    private static final class FinalizerDestroyed {

        private final CountDownLatch allFinalized;

        public FinalizerDestroyed(CountDownLatch allFinalized) {
            this.allFinalized = allFinalized;
        }

//        @Override
//        protected void finalize() throws Throwable {
//            try {
//                //noop here
//            }
//            finally {
//                try {
//                    super.finalize();
//                }
//                finally {
//                    allFinalized.countDown();
//                }
//            }
//        }

    }

    private static final class AutoCloseableDestroyed implements AutoCloseable {

        private final CountDownLatch allClosed;

        public AutoCloseableDestroyed(CountDownLatch allClosed) {
            this.allClosed = allClosed;
        }

        @Override
        public void close() throws Exception {
            try {
                // noop here
            }
            finally {
                allClosed.countDown();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        InputStream in = new FileInputStream("/Users/mstepan/repo/app-java13-template/src/main/java/com/max/app/cleaners" +
                                                     "/names.txt");

        IOException readEx = null;

        try {
            String str = new String(in.readAllBytes(), Charset.defaultCharset());
            System.out.println(str);
        }
        catch (IOException readIoEx) {
            readEx = readIoEx;
        }
        finally {
            safelyCloseStream(in, readEx);
        }

        System.out.printf("FileCleanerMain done. java version %s%n", System.getProperty("java.version"));
    }

    private static void safelyCloseStream(InputStream in, IOException readEx) {
        try {
            in.close();
        }
        catch (IOException closeIoEx) {
            if (readEx == null) {
                throw new IllegalStateException(closeIoEx);
            }
            else {
                closeIoEx.addSuppressed(readEx);
                throw new IllegalStateException(closeIoEx);
            }
        }
    }
}
