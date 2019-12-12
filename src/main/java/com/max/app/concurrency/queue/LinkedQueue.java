package com.max.app.concurrency.queue;

public class LinkedQueue<T> {

    // head will be used as a sentinel Node
    private Node<T> head = new Node<>();
    private Node<T> tail = head;

    private final Object pollLock = new Object();
    private final Object putLock = new Object();

    public void put(T value) {

        Node<T> newNode = new Node<>(value, null);

        synchronized (putLock) {
            synchronized (tail.mutex) {
                tail.next = newNode;
                tail = newNode;
            }
        }

    }

    public T poll() {
        synchronized (pollLock) {
            synchronized (head.mutex) {

                if (head.next == null) {
                    return null;
                }


                Node<T> first = head.next;
                head.next = null;
                head = first;

                T value = head.value;
                head.value = null;

                return value;

            }
        }
    }

    private static final class Node<U> {

        final Object mutex = new Object();

        U value;
        Node<U> next;

        Node() {
            this(null, null);
        }

        Node(U value, Node<U> next) {
            this.value = value;
            this.next = next;
        }
    }
}
