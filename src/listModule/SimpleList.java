package listModule;

public interface SimpleList <E>{
    boolean add (E element);
    void add(int index, E element);
    E remove(int index);
    boolean remove (Object object);
    void clear();
    boolean contains(Object object);
    E get(int index);
    E set(int index, E element);
    int size();
    boolean isEmpty();
}
