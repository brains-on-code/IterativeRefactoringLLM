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
        increaseFrequency(node);
        return node.value;
    }

    public void method2(K key, V value) {
        Node node = cache.get(key);

        if (node != null) {
            node.value = value;
            increaseFrequency(node);
            return;
        }

        if (cache.size() >= capacity) {
            evictLeastFrequent();
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
        while (current != null && current.frequency <= node.frequency) {
            current = current.next;
        }

        if (current == null) {
            appendToTail(node);
        } else {
            insertBefore(current, node);
        }
    }

    private boolean isListEmpty() {
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

    private void removeNode(Node node) {
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