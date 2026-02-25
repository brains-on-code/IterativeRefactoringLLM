package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

public class LFUCache<K, V> {

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

    private Node leastFrequentNode;
    private Node mostFrequentNode;
    private final Map<K, Node> keyToNodeMap;
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
        this.keyToNodeMap = new HashMap<>();
    }

    public V get(K key) {
        Node node = keyToNodeMap.get(key);
        if (node == null) {
            return null;
        }
        removeNode(node);
        node.frequency++;
        insertNodeByFrequency(node);
        return node.value;
    }

    public void put(K key, V value) {
        if (keyToNodeMap.containsKey(key)) {
            Node existingNode = keyToNodeMap.get(key);
            existingNode.value = value;
            existingNode.frequency++;
            removeNode(existingNode);
            insertNodeByFrequency(existingNode);
        } else {
            if (keyToNodeMap.size() >= capacity) {
                keyToNodeMap.remove(leastFrequentNode.key);
                removeNode(leastFrequentNode);
            }
            Node newNode = new Node(key, value, 1);
            insertNodeByFrequency(newNode);
            keyToNodeMap.put(key, newNode);
        }
    }

    private void insertNodeByFrequency(Node nodeToInsert) {
        if (mostFrequentNode != null && leastFrequentNode != null) {
            Node currentNode = leastFrequentNode;
            while (currentNode != null) {
                if (currentNode.frequency > nodeToInsert.frequency) {
                    if (currentNode == leastFrequentNode) {
                        nodeToInsert.next = currentNode;
                        currentNode.previous = nodeToInsert;
                        leastFrequentNode = nodeToInsert;
                        break;
                    } else {
                        nodeToInsert.next = currentNode;
                        nodeToInsert.previous = currentNode.previous;
                        currentNode.previous.next = nodeToInsert;
                        currentNode.previous = nodeToInsert;
                        break;
                    }
                } else {
                    currentNode = currentNode.next;
                    if (currentNode == null) {
                        mostFrequentNode.next = nodeToInsert;
                        nodeToInsert.previous = mostFrequentNode;
                        nodeToInsert.next = null;
                        mostFrequentNode = nodeToInsert;
                        break;
                    }
                }
            }
        } else {
            mostFrequentNode = nodeToInsert;
            leastFrequentNode = mostFrequentNode;
        }
    }

    private void removeNode(Node nodeToRemove) {
        if (nodeToRemove.previous != null) {
            nodeToRemove.previous.next = nodeToRemove.next;
        } else {
            leastFrequentNode = nodeToRemove.next;
        }

        if (nodeToRemove.next != null) {
            nodeToRemove.next.previous = nodeToRemove.previous;
        } else {
            mostFrequentNode = nodeToRemove.previous;
        }
    }
}