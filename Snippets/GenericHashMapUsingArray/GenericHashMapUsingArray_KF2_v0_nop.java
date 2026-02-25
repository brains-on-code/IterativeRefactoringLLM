package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;


public class GenericHashMapUsingArray<K, V> {

    private int size;
    private LinkedList<Node>[] buckets;


    public GenericHashMapUsingArray() {
        initBuckets(16);
        size = 0;
    }


    private void initBuckets(int n) {
        buckets = new LinkedList[n];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
    }


    public void put(K key, V value) {
        int bucketIndex = hashFunction(key);
        LinkedList<Node> nodes = buckets[bucketIndex];
        for (Node node : nodes) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        nodes.add(new Node(key, value));
        size++;

        float loadFactorThreshold = 0.75f;
        if ((float) size / buckets.length > loadFactorThreshold) {
            reHash();
        }
    }


    private int hashFunction(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }


    private void reHash() {
        LinkedList<Node>[] oldBuckets = buckets;
        initBuckets(oldBuckets.length * 2);
        this.size = 0;

        for (LinkedList<Node> nodes : oldBuckets) {
            for (Node node : nodes) {
                put(node.key, node.value);
            }
        }
    }


    public void remove(K key) {
        int bucketIndex = hashFunction(key);
        LinkedList<Node> nodes = buckets[bucketIndex];

        Node target = null;
        for (Node node : nodes) {
            if (node.key.equals(key)) {
                target = node;
                break;
            }
        }

        if (target != null) {
            nodes.remove(target);
            size--;
        }
    }


    public int size() {
        return this.size;
    }


    public V get(K key) {
        int bucketIndex = hashFunction(key);
        LinkedList<Node> nodes = buckets[bucketIndex];
        for (Node node : nodes) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (LinkedList<Node> nodes : buckets) {
            for (Node node : nodes) {
                builder.append(node.key);
                builder.append(" : ");
                builder.append(node.value);
                builder.append(", ");
            }
        }
        if (builder.length() > 1) {
            builder.setLength(builder.length() - 2);
        }
        builder.append("}");
        return builder.toString();
    }


    public boolean containsKey(K key) {
        return get(key) != null;
    }


    public class Node {
        K key;
        V value;


        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
