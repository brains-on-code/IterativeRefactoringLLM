package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;

public class SortedLinkedList {

    private Node head;
    private Node tail;

    public SortedLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void insert(int value) {
        Node newNode = new Node(value);

        if (isEmpty()) {
            insertIntoEmptyList(newNode);
            return;
        }

        if (value <= head.value) {
            insertAtHead(newNode);
            return;
        }

        if (value >= tail.value) {
            insertAtTail(newNode);
            return;
        }

        insertInMiddle(newNode);
    }

    private void insertIntoEmptyList(Node newNode) {
        head = newNode;
        tail = newNode;
    }

    private void insertAtHead(Node newNode) {
        newNode.next = head;
        head = newNode;
    }

    private void insertAtTail(Node newNode) {
        tail.next = newNode;
        tail = newNode;
    }

    private void insertInMiddle(Node newNode) {
        Node current = head;

        while (current.next != null && current.next.value < newNode.value) {
            current = current.next;
        }

        newNode.next = current.next;
        current.next = newNode;

        if (newNode.next == null) {
            tail = newNode;
        }
    }

    public boolean delete(int value) {
        if (isEmpty()) {
            return false;
        }

        if (head.value == value) {
            deleteHead();
            return true;
        }

        return deleteNonHead(value);
    }

    private void deleteHead() {
        if (head.next == null) {
            head = null;
            tail = null;
            return;
        }
        head = head.next;
    }

    private boolean deleteNonHead(int value) {
        Node current = head;

        while (current.next != null) {
            Node nextNode = current.next;

            if (nextNode.value == value) {
                unlinkNode(current, nextNode);
                return true;
            }

            current = nextNode;
        }

        return false;
    }

    private void unlinkNode(Node previous, Node toRemove) {
        if (toRemove == tail) {
            tail = previous;
        }
        previous.next = toRemove.next;
    }

    public boolean search(int value) {
        Node current = head;

        while (current != null && current.value <= value) {
            if (current.value == value) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        List<String> elements = new ArrayList<>();
        Node current = head;

        while (current != null) {
            elements.add(String.valueOf(current.value));
            current = current.next;
        }

        return "[" + String.join(", ", elements) + "]";
    }

    public static final class Node {
        public final int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }
}