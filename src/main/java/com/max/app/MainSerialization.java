package com.max.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class MainSerialization {

    private static final Logger LOG = LoggerFactory.getLogger(MainSerialization.class);

    static class MyUser implements java.io.Serializable {

        private static final long serialVersionUID = 874612843535942460L;

        String firstName;
        String lastName;
        int age;

        transient int hash;

        MyUser(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }

        @Override
        public int hashCode() {
            if (hash == 0) {
                hash = Objects.hash(firstName, lastName, age);
            }
            return hash;
        }

        @Override
        public boolean equals(Object obj) {

            if (obj == this) {
                return true;
            }

            if (obj == null || obj.getClass() != MyUser.class) {
                return false;
            }

            MyUser other = (MyUser) obj;

            return Objects.equals(firstName, other.firstName) &&
                    Objects.equals(lastName, other.lastName) &&
                    Objects.equals(age, other.age);

        }

        @Override
        public String toString() {
            return firstName + ", " + lastName + ", age: " + age;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
            out.writeUTF(firstName);
            out.writeUTF(lastName);
            out.writeInt(age);
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            firstName = in.readUTF();
            lastName = in.readUTF();
            age = in.readInt();
        }
    }

    public static void main(String[] args) throws Exception {

        MyUser user1 = new MyUser("Maksym", "Stepanenko", 34);

        Path path = Paths.get("/Users/mstepan/repo/app-java13-template/my-user.bin");
        Files.deleteIfExists(path);
        Files.createFile(path);

        writeToFile(user1, path);

        MyUser user2 = readFromFile(path, MyUser.class);

        LOG.info(String.valueOf(user1));
        LOG.info(String.valueOf(user2));

        if (user1 == user2) {
            LOG.info("Same");
        }
        else {
            LOG.info("Not same");
        }

        if (user1 != null && user1.equals(user2)) {
            LOG.info("Equals");
        }
        else {
            LOG.info("Not equals");
        }

        LOG.info("Main done. java version {}", System.getProperty("java.version"));
    }

    /*
     * Should hang during deserialization when itCount ~ 30
     */
    @SuppressWarnings("unused")
    private static Set<Object> createDeserializationBomb(int itCount) {

        Set<Object> root = new HashSet<>();

        Set<Object> left = new HashSet<>();
        Set<Object> right = new HashSet<>();

        root.add(left);
        root.add(right);

        for (int i = 0; i < itCount; ++i) {

            Set<Object> t1 = new HashSet<>();
            Set<Object> t2 = new HashSet<>();
            t1.add("some value");

            left.add(t1);
            left.add(t2);

            right.add(t1);
            right.add(t2);

            left = t1;
            right = t2;
        }

        return root;
    }

    private static <T> void writeToFile(T obj, Path path) throws IOException {

        assert path != null : "null path parameter passed";

        try (OutputStream out = Files.newOutputStream(path);
             ObjectOutputStream dataOut = new ObjectOutputStream(out)) {
            dataOut.writeObject(obj);
        }
    }

    private static <T> T readFromFile(Path path, Class<T> clazz) throws IOException, ClassNotFoundException {
        assert path != null : "null path parameter passed";
        assert Files.exists(path) : "File doesn't exists: " + path.toString();
        assert clazz != null : "null 'class' parameter passed";

        try (InputStream in = Files.newInputStream(path);
             ObjectInputStream dataIn = new ObjectInputStream(in)) {

            dataIn.setObjectInputFilter(createClazzFilter());

            return clazz.cast(dataIn.readObject());
        }

    }

    private static final Set<Class<?>> ALLOWED_CLASSES = new HashSet<>();

    static {
        ALLOWED_CLASSES.add(MyUser.class);
    }

    private static ObjectInputFilter createClazzFilter() {
        return filterInfo -> {
            if (filterInfo.serialClass() != null &&
                    !ALLOWED_CLASSES.contains(filterInfo.serialClass())) {

                LOG.info("{} not allowed for deserialization", filterInfo.serialClass());

                return ObjectInputFilter.Status.REJECTED;
            }

            return ObjectInputFilter.Status.UNDECIDED;
        };
    }

}
