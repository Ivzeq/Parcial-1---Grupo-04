package setModule;

public class SimpleArraySet<E> implements SimpleSet<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;

    public SimpleArraySet() {
        data = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean add(E element) {
        if (contains(element)) return false;
        if (size == data.length) resize();
        data[size++] = element;
        return true;
    }

    @Override
    public boolean remove(E element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) {
                data[i] = data[--size];
                data[size] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element)) return true;
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) data[i] = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public int size() { return size; }

    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(data, 0, result, 0, size);
        return (E[]) result;
    }

    @Override
    public SimpleSet<E> unionWith(SimpleSet<E> other) {
        SimpleSet<E> result = new SimpleArraySet<>();
        for (E e : this.toArray()) result.add(e);
        for (E e : other.toArray()) result.add(e);
        return result;
    }

    @Override
    public SimpleSet<E> intersectWith(SimpleSet<E> other) {
        SimpleSet<E> result = new SimpleArraySet<>();
        for (E e : this.toArray()) {
            if (other.contains(e)) result.add(e);
        }
        return result;
    }

    @Override
    public SimpleSet<E> differenceWith(SimpleSet<E> other) {
        SimpleSet<E> result = new SimpleArraySet<>();
        for (E e : this.toArray()) {
            if (!other.contains(e)) result.add(e);
        }
        return result;
    }

    private void resize() {
        Object[] newData = new Object[data.length * 2];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    @Override
    public String toString() {
        if (size == 0) return "{}";
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append("}").toString();
    }
}