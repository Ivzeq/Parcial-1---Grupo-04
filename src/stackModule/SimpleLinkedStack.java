package stackModule;

import java.util.NoSuchElementException;

public class SimpleLinkedStack<E> implements SimpleStack<E> {
    private static class Node<E> {
        E data;
        Node<E> next; // points toward the bottom of the stack

        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<E> top;

    private int size;

    public SimpleLinkedStack() {
        top  = null;
        size = 0;
    }

    @Override
    public void push(E element) {
        top  = new Node<>(element, top); // new node's next → old top
        size++;
    }

    @Override
    public E pop() {
        checkNotEmpty();
        E data = top.data;
        top    = top.next; // advance head toward the bottom
        size--;
        return data;
    }

    @Override
    public E peek() {
        checkNotEmpty();
        return top.data;
    }

    @Override
    public void clear() {
        // Sever all links so the GC can reclaim every node.
        while (top != null) {
            Node<E> next = top.next;
            top.data = null;
            top.next = null;
            top      = next;
        }
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
        // Collect nodes top→bottom, then reverse for bottom→top display.
        Object[] elements = new Object[size];
        Node<E> current   = top;
        for (int i = size - 1; i >= 0; i--) {
            elements[i] = current.data;
            current     = current.next;
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i]);
            if (i == elements.length - 1) sb.append(" ←top");
            else sb.append(", ");
        }
        return sb.append("]").toString();
    }

    private void checkNotEmpty() {
        if (isEmpty()) throw new NoSuchElementException("La pila está vacía.");
    }
}