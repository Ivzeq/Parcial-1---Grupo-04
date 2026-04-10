package queueModule;

import java.util.NoSuchElementException;
@SuppressWarnings("unchecked")
public class SimpleArrayQueue<E> implements SimpleQueue<E> {
    private static final int DEFAULT_CAPACITY = 4;

    private Object[] data;

    private int head;

    private int tail;

    private int size;

    public SimpleArrayQueue() {
        data = new Object[DEFAULT_CAPACITY];
        head = 0;
        tail = 0;
        size = 0;
    }

    public SimpleArrayQueue(int initialCapacity) {
        if (initialCapacity < 0) throw new IllegalArgumentException("Negative capacity: " + initialCapacity);
        data = new Object[Math.max(initialCapacity, 1)];
        head = 0;
        tail = 0;
        size = 0;
    }

    @Override
    public void enqueue(E element) {
        ensureCapacity(size + 1);
        data[tail] = element;
        tail       = (tail + 1) % data.length; // wrap around
        size++;
    }

    @Override
    public E dequeue() {
        checkNotEmpty();
        E front  = (E) data[head];
        data[head] = null;                     // help GC
        head       = (head + 1) % data.length; // advance with wrap-around
        size--;
        return front;
    }

    @Override
    public E peek() {
        checkNotEmpty();
        return (E) data[head];
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[(head + i) % data.length] = null;
        }
        head = 0;
        tail = 0;
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
        for (int i = 0; i < size; i++) {
            sb.append(data[(head + i) % data.length]);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append(" ←rear]").toString();
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= data.length) return;

        int newCapacity = Math.max(minCapacity, data.length * 2);
        Object[] newData = new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newData[i] = data[(head + i) % data.length];
        }

        data = newData;
        head = 0;
        tail = size;
    }

    private void checkNotEmpty() {
        if (isEmpty()) throw new NoSuchElementException("La cola está vacía.");
    }
}