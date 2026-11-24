package task2;

public class CircularBuffer<T> {
    private static class Node<E> {
        E data;
        Node<E> next;
    }

    private final int capacity;
    private Node<T> headNode;
    private Node<T> tailNode;
    private int headIndex = 0;
    private int tailIndex = 0;
    private int size = 0;

    public CircularBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Розмір буфера має бути більшим за 0");
        }
        this.capacity = capacity;

        Node<T> first = new Node<>();
        Node<T> current = first;
        for (int i = 1; i < capacity; i++) {
            Node<T> next = new Node<>();
            current.next = next;
            current = next;
        }
        current.next = first;

        this.headNode = first;
        this.tailNode = first;
    }

    public synchronized void put(T item) throws InterruptedException {
        while (size == capacity) {
            wait();
        }

        tailNode.data = item;
        tailNode = tailNode.next;
        tailIndex = (tailIndex + 1) % capacity;
        size++;

        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (size == 0) {
            wait();
        }

        T item = headNode.data;
        headNode.data = null;
        headNode = headNode.next;
        headIndex = (headIndex + 1) % capacity;
        size--;

        notifyAll();
        return item;
    }

    public synchronized int size() {
        return size;
    }

    public synchronized boolean isEmpty() {
        return size == 0;
    }

    public synchronized boolean isFull() {
        return size == capacity;
    }
}
