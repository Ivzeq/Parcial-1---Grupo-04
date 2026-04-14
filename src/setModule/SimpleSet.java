package setModule;

public interface SimpleSet <E> {
    public boolean add(E element);
    public boolean remove(E element);
    public boolean contains(E element);
    public void clear();
    public boolean isEmpty();
    public int size();
    public E[] toArray();
    public SimpleSet<E> unionWith(SimpleSet<E> other);
    public SimpleSet<E> intersectWith(SimpleSet<E> other);
    public SimpleSet<E> differenceWith(SimpleSet<E> other);
}
