package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;

public class SortedLinkedList {
    private Node headNode;
    private Node tailNode;

    public SortedLinkedList() {
        this.headNode = null;
        this.tailNode = null;
    }

    public void insert(int value) {
        Node newNode = new Node(value);

        if (headNode == null) {
            headNode = newNode;
            tailNode = newNode;
            return;
        }

        if (value < headNode.value) {
            newNode.next = headNode;
            headNode = newNode;
            return;
        }

        if (value > tailNode.value) {
            tailNode.next = newNode;
            tailNode = newNode;
            return;
        }

        Node currentNode = headNode;
        while (currentNode.next != null && currentNode.next.value < value) {
            currentNode = currentNode.next;
        }

        newNode.next = currentNode.next;
        currentNode.next = newNode;

        if (newNode.next == null) {
            tailNode = newNode;
        }
    }

    public boolean delete(int value) {
        if (headNode == null) {
            return false;
        }

        if (headNode.value == value) {
            if (headNode.next == null) {
                headNode = null;
                tailNode = null;
            } else {
                headNode = headNode.next;
            }
            return true;
        }

        Node currentNode = headNode;
        while (currentNode.next != null) {
            if (currentNode.next.value == value) {
                if (currentNode.next == tailNode) {
                    tailNode = currentNode;
                }
                currentNode.next = currentNode.next.next;
                return true;
            }
            currentNode = currentNode.next;
        }

        return false;
    }

    public boolean search(int value) {
        Node currentNode = headNode;
        while (currentNode != null) {
            if (currentNode.value == value) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    public boolean isEmpty() {
        return headNode == null;
    }

    @Override
    public String toString() {
        if (headNode == null) {
            return "[]";
        }

        List<String> elementStrings = new ArrayList<>();
        Node currentNode = headNode;
        while (currentNode != null) {
            elementStrings.add(String.valueOf(currentNode.value));
            currentNode = currentNode.next;
        }
        return "[" + String.join(", ", elementStrings) + "]";
    }

    public final class Node {
        public final int value;
        public Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }
    }
}