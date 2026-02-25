package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

public class Class1<K, V> {

    private static final int DEFAULT_CAPACITY = 100;

    private class Node {
        private final K key;
        private V value;
        private int frequency;
        private Node prev;
        private Node next;

        Node(K key, V value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }

    private Node head;
    private Node tail;
    private final Map<K, Node> cache;
    private final int capacity;

    public Class1() {
        this(DEFAULT_CAPACITY);
    }

    public Class1(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    public V method1(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return null;
        }
        incrementFrequency(node);
        return node.value;
    }

    public void method2(K key, V value) {
        Node existingNode = cache.get(key);

        if (existingNode != null) {
            existingNode.value = value;
            incrementFrequency(existingNode);
            return;
        }

        if (cache.size() >= capacity) {
            evictLeastFrequent();
        }

        Node newNode = new Node(key, value, 1);
        insertNodeByFrequency(newNode);
        cache.put(key, newNode);
    }

    private void incrementFrequency(Node node) {
        removeNode(node);
        node.frequency++;
        insertNodeByFrequency(node);
    }

    private void evictLeastFrequent() {
        if (head == null) {
            return;
        }
        cache.remove(head.key);
        removeNode(head);
    }

    private void insertNodeByFrequency(Node node) {
        if (isListEmpty()) {
            initializeList(node);
            return;
        }

        Node current = head;
        while (current != null) {
            if (current.frequency > node.frequency) {
                insertBefore(current, node);
                return;
            }
            current = current.next;
        }

        appendToTail(node);
    }

    private boolean isListEmpty() {
        return head == null || tail == null;
    }

    private void initializeList(Node node) {
        head = node;
        tail = node;
    }

    private void insertBefore(Node existingNode, Node newNode) {
        newNode.next = existingNode;
        newNode.prev = existingNode.prev;

        if (existingNode.prev != null) {
            existingNode.prev.next = newNode;
        } else {
            head = newNode;
        }

        existingNode.prev = newNode;
    }

    private void appendToTail(Node node) {
        tail.next = node;
        node.prev = tail;
        node.next = null;
        tail = node;
    }

    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        node.prev = null;
        node.next = null;
    }
}