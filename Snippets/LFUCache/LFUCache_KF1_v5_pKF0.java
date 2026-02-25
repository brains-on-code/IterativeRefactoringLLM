package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

public class LfuCache<K, V> {

    private static final int DEFAULT_CAPACITY = 100;

    private class Node {
        private final K key;
        private V value;
        private int frequency;
        private Node prev;
        private Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    private Node head;
    private Node tail;
    private final Map<K, Node> cache;
    private final int capacity;

    public LfuCache() {
        this(DEFAULT_CAPACITY);
    }

    public LfuCache(int capacity) {
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
        incrementFrequency(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node existingNode = cache.get(key);

        if (existingNode != null) {
            existingNode.value = value;
            incrementFrequency(existingNode);
            return;
        }

        if (cache.size() >= capacity) {
            evictLeastFrequent();
        }

        Node newNode = new Node(key, value);
        insertByFrequency(newNode);
        cache.put(key, newNode);
    }

    private void incrementFrequency(Node node) {
        removeFromList(node);
        node.frequency++;
        insertByFrequency(node);
    }

    private void evictLeastFrequent() {
        if (head == null) {
            return;
        }
        cache.remove(head.key);
        removeFromList(head);
    }

    private void insertByFrequency(Node node) {
        if (isEmpty()) {
            initializeList(node);
            return;
        }

        Node current = head;
        while (current != null && current.frequency <= node.frequency) {
            current = current.next;
        }

        if (current == null) {
            appendToTail(node);
        } else {
            insertBefore(current, node);
        }
    }

    private boolean isEmpty() {
        return head == null;
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
        if (tail == null) {
            initializeList(node);
            return;
        }
        tail.next = node;
        node.prev = tail;
        node.next = null;
        tail = node;
    }

    private void removeFromList(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;

        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode;
        }

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }

        node.prev = null;
        node.next = null;
    }
}