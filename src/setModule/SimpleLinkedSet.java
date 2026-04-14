package setModule;

public class SimpleLinkedSet<E> implements SimpleSet<E> {

    private static class Node<E> {
        E data;
        Node<E> next;
        Node(E data) { this.data = data; }
    }

    private Node<E> head;
    private int size;

    @Override
    public boolean add(E element) {
        if (contains(element)) return false;
        Node<E> node = new Node<>(element);
        node.next = head;
        head = node;
        size++;
        return true;
    }

    @Override
    public boolean remove(E element) {
        if (head == null) return false;
        if (head.data.equals(element)) {
            head = head.next;
            size--;
            return true;
        }
        Node<E> current = head;
        while (current.next != null) {
            if (current.next.data.equals(element)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean contains(E element) {
        Node<E> current = head;
        while (current != null) {
            if (current.data.equals(element)) return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public void clear() { head = null; size = 0; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public int size() { return size; }

    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray() {
        Object[] result = new Object[size];
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            result[i] = current.data;
            current = current.next;
        }
        return (E[]) result;
    }

    @Override
    public SimpleSet<E> unionWith(SimpleSet<E> other) {
        SimpleSet<E> result = new SimpleLinkedSet<>();
        for (E e : this.toArray()) result.add(e);
        for (E e : other.toArray()) result.add(e);
        return result;
    }

    @Override
    public SimpleSet<E> intersectWith(SimpleSet<E> other) {
        SimpleSet<E> result = new SimpleLinkedSet<>();
        for (E e : this.toArray()) {
            if (other.contains(e)) result.add(e);
        }
        return result;
    }

    @Override
    public SimpleSet<E> differenceWith(SimpleSet<E> other) {
        SimpleSet<E> result = new SimpleLinkedSet<>();
        for (E e : this.toArray()) {
            if (!other.contains(e)) result.add(e);
        }
        return result;
    }

    @Override
    public String toString() {
        if (size == 0) return "{}";
        StringBuilder sb = new StringBuilder("{");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) sb.append(", ");
            current = current.next;
        }
        return sb.append("}").toString();
    }
}