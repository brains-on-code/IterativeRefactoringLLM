package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

public class LFUCache<K, V> {

    private static final int DEFAULT_CAPACITY = 100;

    private final Map<K, Node> cache;
    private final int capacity;

    private Node head;
    private Node tail;

    private class Node {
        private final K key;
        private V value;
        private int frequency;
        private Node previous;
        private Node next;

        Node(K key, V value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }

    public LFUCache() {
        this(DEFAULT_CAPACITY);
    }

    public LFUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    public V get(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return null;
        }
        increaseFrequency(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node existingNode = cache.get(key);

        if (existingNode != null) {
            existingNode.value = value;
            increaseFrequency(existingNode);
            return;
        }

        if (cache.size() >= capacity) {
            evictLeastFrequentlyUsed();
        }

        Node newNode = new Node(key, value, 1);
        insertNodeByFrequency(newNode);
        cache.put(key, newNode);
    }

    private void increaseFrequency(Node node) {
        removeNode(node);
        node.frequency++;
        insertNodeByFrequency(node);
    }

    private void evictLeastFrequentlyUsed() {
        if (head == null) {
            return;
        }
        cache.remove(head.key);
        removeNode(head);
    }

    private void insertNodeByFrequency(Node node) {
        if (head == null) {
            head = node;
            tail = node;
            return;
        }

        Node current = head;

        while (current != null && current.frequency <= node.frequency) {
            current = current.next;
        }

        if (current == head) {
            node.next = head;
            head.previous = node;
            head = node;
        } else if (current == null) {
            tail.next = node;
            node.previous = tail;
            tail = node;
        } else {
            Node previous = current.previous;
            node.next = current;
            node.previous = previous;
            previous.next = node;
            current.previous = node;
        }
    }

    private void removeNode(Node node) {
        Node previous = node.previous;
        Node next = node.next;

        if (previous != null) {
            previous.next = next;
        } else {
            head = next;
        }

        if (next != null) {
            next.previous = previous;
        } else {
            tail = previous;
        }

        node.previous = null;
        node.next = null;
    }
}