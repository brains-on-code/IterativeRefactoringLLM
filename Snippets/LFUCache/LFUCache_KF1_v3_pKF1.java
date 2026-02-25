package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

public class LFUCache<K, V> {

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

    private Node head; // least frequently used
    private Node tail; // most frequently used
    private final Map<K, Node> cache;
    private final int capacity;
    private static final int DEFAULT_CAPACITY = 100;

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
        removeNode(node);
        node.frequency++;
        insertNodeByFrequency(node);
        return node.value;
    }

    public void put(K key, V value) {
        Node node = cache.get(key);

        if (node != null) {
            node.value = value;
            node.frequency++;
            removeNode(node);
            insertNodeByFrequency(node);
            return;
        }

        if (cache.size() >= capacity) {
            cache.remove(head.key);
            removeNode(head);
        }

        Node newNode = new Node(key, value, 1);
        insertNodeByFrequency(newNode);
        cache.put(key, newNode);
    }

    private void insertNodeByFrequency(Node node) {
        if (head != null && tail != null) {
            Node current = head;
            while (current != null) {
                if (current.frequency > node.frequency) {
                    if (current == head) {
                        node.next = current;
                        current.prev = node;
                        head = node;
                        break;
                    } else {
                        node.next = current;
                        node.prev = current.prev;
                        current.prev.next = node;
                        current.prev = node;
                        break;
                    }
                } else {
                    current = current.next;
                    if (current == null) {
                        tail.next = node;
                        node.prev = tail;
                        node.next = null;
                        tail = node;
                        break;
                    }
                }
            }
        } else {
            head = node;
            tail = node;
        }
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
    }
}