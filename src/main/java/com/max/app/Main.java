package com.max.app;

import java.util.HashSet;
import java.util.Set;

public final class Main {

    public static void main(String[] args) throws Exception {

        Set<String> set = new HashSet<>();
        set.add("first");
        set.add("second");
        set.add("third");

        for (String str : set) {
            System.out.println(str);
        }

        System.out.printf("java: %s%n", System.getProperty("java.version"));
    }


}
