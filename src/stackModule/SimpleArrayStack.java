package stackModule;

import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class SimpleArrayStack<E> implements SimpleStack<E> {

    private static final int DEFAULT_CAPACITY = 4;

    private Object[] data;

    private int size;

    public SimpleArrayStack() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public SimpleArrayStack(int initialCapacity) {
        if (initialCapacity < 0) throw new IllegalArgumentException("Negative capacity: " + initialCapacity);
        data = new Object[Math.max(initialCapacity, 1)];
        size = 0;
    }

    @Override
    public void push(E element) {
        ensureCapacity(size + 1);
        data[size] = element;
        size++;
    }

    @Override
    public E pop() {
        checkNotEmpty();
        E top = (E) data[size - 1];
        data[size - 1] = null; // help GC
        size--;
        return top;
    }

    @Override
    public E peek() {
        checkNotEmpty();
        return (E) data[size - 1];
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) data[i] = null;
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
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i == size - 1) sb.append(" ←top");
            else sb.append(", ");
        }
        return sb.append("]").toString();
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(minCapacity, data.length * 2);
            Object[] newData = new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    private void checkNotEmpty() {
        if (isEmpty()) throw new NoSuchElementException("La pila está vacía.");
    }
}