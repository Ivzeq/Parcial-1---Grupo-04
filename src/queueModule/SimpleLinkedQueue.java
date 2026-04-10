package queueModule;

import java.util.NoSuchElementException;

public class SimpleLinkedQueue<E> implements SimpleQueue<E> {

    private static class Node<E> {
        E       data;
        Node<E> prev; // toward the front (head)
        Node<E> next; // toward the rear  (tail)

        Node(E data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    private Node<E> head;

    private Node<E> tail;

    private int size;

    public SimpleLinkedQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void enqueue(E element) {
        Node<E> newNode = new Node<>(element);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            // newNode becomes the new tail.
            newNode.prev = tail;
            tail.next    = newNode;
            tail         = newNode;
        }
        size++;
    }

    @Override
    public E dequeue() {
        checkNotEmpty();
        E data = head.data;

        if (size == 1) {
            // Queue had exactly one element; now it's empty.
            head = null;
            tail = null;
        } else {
            head      = head.next;
            head.prev = null;
        }

        size--;
        return data;
    }

    @Override
    public E peek() {
        checkNotEmpty();
        return head.data;
    }

    @Override
    public void clear() {
        // Sever all links to aid GC.
        Node<E> current = head;
        while (current != null) {
            Node<E> next   = current.next;
            current.data   = null;
            current.prev   = null;
            current.next   = null;
            current        = next;
        }
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder("[frente→ ");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(", ");
            current = current.next;
        }
        return sb.append(" ←rear]").toString();
    }

    // -----------------------------------------------------------------------
    // Private helpers
    // -----------------------------------------------------------------------

    private void checkNotEmpty() {
        if (isEmpty()) throw new NoSuchElementException("La cola está vacía.");
    }
}