package listModule;

public class SimpleLinkedList <E> implements SimpleList<E>{
    private class Node {
        E data;
        Node previous;
        Node next;

        Node(E data, Node previous, Node next) {
            this.data = data;
            this.previous = null;
            this.next = null;
        }
    }

    private Node start;
    private Node end;
    private int size;

    public SimpleLinkedList() {
        start = null;
        end = null;
        size = 0;
    }

    @Override
    public boolean add(E element) {
        if (start == null && end == null) {
            start = new Node(element, null, null);
            end = start;
        } else if(end != null) {
            Node newNode = new Node(element, end, null);
            end.next = newNode;
            end = newNode;
        }

        size ++;

        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            Node newNode = new Node(element, null, start);
            if (start != null) start.previous = newNode;
            start = newNode;
            if (size == 0) end = newNode;

        } else if (index == size) {
            add(element);

        } else {
            Node nodeNext = start;
            for (int i = 0; i < index; i++) {
                nodeNext = nodeNext.next;
            }

            Node nodePrevious = nodeNext.previous;
            Node newNode = new Node(element, nodePrevious, nodeNext);

            nodePrevious.next = newNode;
            nodeNext.previous = newNode;
        }

        size++;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        E elementRemoved = null;

        if (index == 0) {
            elementRemoved = start.data;

            if (start == end) {
                start = null;
                end = null;
            } else {
                start = start.next;
                start.previous = null;
            }

        } else if (index == size - 1) {
            elementRemoved = end.data;

            if (start == end) {
                start = null;
                end = null;
            } else {
                end = end.previous;
                end.next = null;
            }


        } else {
            Node nodeToRemove = start;
            for (int i = 0; i < index; i++) {
                nodeToRemove = nodeToRemove.next;
            }

            elementRemoved = nodeToRemove.data;

            Node nodePrevious = nodeToRemove.previous;
            Node nodeNext = nodeToRemove.next;

            nodePrevious.next = nodeNext;
            nodeNext.previous = nodePrevious;
        }

        size--;
        return elementRemoved;

    }

    @Override
    public boolean remove(Object object) {
        Node nodeCurrent = start;

        while (nodeCurrent != null) {
            boolean match = (object == null) ? nodeCurrent.data == null : object.equals(nodeCurrent.data);

            if (match) {
                if (nodeCurrent == start && nodeCurrent == end) {
                    start = null;
                    end = null;
                } else if (nodeCurrent == start) {
                    start = start.next;
                    start.previous = null;
                } else if (nodeCurrent == end) {
                    end = end.previous;
                    end.next = null;
                } else {
                    nodeCurrent.previous.next = nodeCurrent.next;
                    nodeCurrent.next.previous = nodeCurrent.previous;
                }
                size--;
                return true;
            }
            nodeCurrent = nodeCurrent.next;
        }
        return false;
    }

    @Override
    public void clear() {
        start = null;
        end = null;
        size = 0;
    }

    @Override
    public boolean contains(Object object) {
        Node nodeCurrent = start;

        while (nodeCurrent != null) {
            boolean match = (object == null) ? nodeCurrent.data == null : object.equals(nodeCurrent.data);
            if (match) return true;
            nodeCurrent = nodeCurrent.next;
        }
        return false;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) return start.data;
        if (index == size - 1) return end.data;

        Node nodeCurrent = start;

        for (int i = 0; i < index; i++) {
            nodeCurrent = nodeCurrent.next;
        }

        return nodeCurrent.data;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        E oldData;

        if (index == 0) {
            oldData = start.data;
            start.data = element;
            return oldData;
        }
        if (index == size - 1) {
            oldData = end.data;
            end.data = element;
            return oldData;
        }

        Node nodeCurrent = start;

        for (int i = 0; i < index; i++) {
            nodeCurrent = nodeCurrent.next;
        }

        oldData = nodeCurrent.data;
        nodeCurrent.data = element;

        return oldData;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
