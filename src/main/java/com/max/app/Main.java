package com.max.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.PriorityQueue;
import java.util.Queue;

public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        Queue<Integer> priorityQueue = new PriorityQueue<>();

        priorityQueue.add(1);
        priorityQueue.add(2);
        priorityQueue.add(3);

        System.out.println(priorityQueue.poll());


        LOG.info("Main done. java version {}", System.getProperty("java.version"));
    }

}
