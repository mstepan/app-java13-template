package com.max.app;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Main {

    public static void main(String[] args) throws Exception {


        List<String> data  = new CopyOnWriteArrayList<>();

        data.add("one");
        data.add("two");
        data.add("three");

        System.out.printf("java: %s%n", System.getProperty("java.version"));
    }
    

}
