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
        Node existingNode = cache.get(key);

        if (existingNode != null) {
            existingNode.value = value;
            existingNode.frequency++;
            removeNode(existingNode);
            insertNodeByFrequency(existingNode);
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

    private void insertNodeByFrequency(Node nodeToInsert) {
        if (head != null && tail != null) {
            Node current = head;
            while (current != null) {
                if (current.frequency > nodeToInsert.frequency) {
                    if (current == head) {
                        nodeToInsert.next = current;
                        current.prev = nodeToInsert;
                        head = nodeToInsert;
                        break;
                    } else {
                        nodeToInsert.next = current;
                        nodeToInsert.prev = current.prev;
                        current.prev.next = nodeToInsert;
                        current.prev = nodeToInsert;
                        break;
                    }
                } else {
                    current = current.next;
                    if (current == null) {
                        tail.next = nodeToInsert;
                        nodeToInsert.prev = tail;
                        nodeToInsert.next = null;
                        tail = nodeToInsert;
                        break;
                    }
                }
            }
        } else {
            head = nodeToInsert;
            tail = nodeToInsert;
        }
    }

    private void removeNode(Node nodeToRemove) {
        if (nodeToRemove.prev != null) {
            nodeToRemove.prev.next = nodeToRemove.next;
        } else {
            head = nodeToRemove.next;
        }

        if (nodeToRemove.next != null) {
            nodeToRemove.next.prev = nodeToRemove.prev;
        } else {
            tail = nodeToRemove.prev;
        }
    }
}