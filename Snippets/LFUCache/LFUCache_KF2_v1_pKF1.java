package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

public class LFUCache<K, V> {

    private class CacheNode {
        private final K key;
        private V value;
        private int frequency;
        private CacheNode previous;
        private CacheNode next;

        CacheNode(K key, V value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }

    private CacheNode leastFrequentNode;
    private CacheNode mostFrequentNode;
    private final Map<K, CacheNode> keyToNodeMap;
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
        CacheNode node = keyToNodeMap.get(key);
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
            CacheNode existingNode = keyToNodeMap.get(key);
            existingNode.value = value;
            existingNode.frequency++;
            removeNode(existingNode);
            insertNodeByFrequency(existingNode);
        } else {
            if (keyToNodeMap.size() >= capacity) {
                keyToNodeMap.remove(leastFrequentNode.key);
                removeNode(leastFrequentNode);
            }
            CacheNode newNode = new CacheNode(key, value, 1);
            insertNodeByFrequency(newNode);
            keyToNodeMap.put(key, newNode);
        }
    }

    private void insertNodeByFrequency(CacheNode node) {
        if (mostFrequentNode != null && leastFrequentNode != null) {
            CacheNode current = leastFrequentNode;
            while (current != null) {
                if (current.frequency > node.frequency) {
                    if (current == leastFrequentNode) {
                        node.next = current;
                        current.previous = node;
                        leastFrequentNode = node;
                        break;
                    } else {
                        node.next = current;
                        node.previous = current.previous;
                        current.previous.next = node;
                        current.previous = node;
                        break;
                    }
                } else {
                    current = current.next;
                    if (current == null) {
                        mostFrequentNode.next = node;
                        node.previous = mostFrequentNode;
                        node.next = null;
                        mostFrequentNode = node;
                        break;
                    }
                }
            }
        } else {
            mostFrequentNode = node;
            leastFrequentNode = mostFrequentNode;
        }
    }

    private void removeNode(CacheNode node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        } else {
            leastFrequentNode = node.next;
        }

        if (node.next != null) {
            node.next.previous = node.previous;
        } else {
            mostFrequentNode = node.previous;
        }
    }
}