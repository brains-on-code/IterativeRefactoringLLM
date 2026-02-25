package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

public class LFUCache<K, V> {

    private static final int DEFAULT_CAPACITY = 100;

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

    private Node head;
    private Node tail;
    private final Map<K, Node> cache;
    private final int capacity;

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
        incrementFrequency(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node node = cache.get(key);

        if (node != null) {
            node.value = value;
            incrementFrequency(node);
            return;
        }

        if (cache.size() >= capacity) {
            evictLeastFrequentlyUsed();
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
            insertAtHead(node);
        } else if (current == null) {
            insertAtTail(node);
        } else {
            insertBefore(node, current);
        }
    }

    private void insertAtHead(Node node) {
        node.next = head;
        head.previous = node;
        head = node;
    }

    private void insertAtTail(Node node) {
        tail.next = node;
        node.previous = tail;
        tail = node;
    }

    private void insertBefore(Node node, Node current) {
        Node previousNode = current.previous;
        node.next = current;
        node.previous = previousNode;
        previousNode.next = node;
        current.previous = node;
    }

    private void removeNode(Node node) {
        Node previousNode = node.previous;
        Node nextNode = node.next;

        if (previousNode != null) {
            previousNode.next = nextNode;
        } else {
            head = nextNode;
        }

        if (nextNode != null) {
            nextNode.previous = previousNode;
        } else {
            tail = previousNode;
        }

        node.previous = null;
        node.next = null;
    }
}