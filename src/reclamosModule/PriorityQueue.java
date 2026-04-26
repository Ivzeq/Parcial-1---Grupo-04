package reclamosModule;

/**
 * TDA Cola con Prioridad.
 *
 * Decisión de diseño: Se modela como una cola donde los elementos se
 * desencolan en orden de mayor a menor prioridad. A igual prioridad
 * se respeta el orden de inserción (FIFO dentro del mismo nivel),
 * lo que refleja el comportamiento natural de una mesa de ayuda.
 *
 * El TDA lanza excepciones ante datos inválidos; es responsabilidad
 * de la aplicación nunca invocar operaciones con datos incorrectos.
 */
public interface PriorityQueue<E> {

    /**
     * Inserta un elemento con la prioridad dada.
     *
     * @param element  el elemento a insertar; no puede ser null
     * @param priority valor de prioridad; debe estar en [1..4]
     *                 (1 = Bajo, 2 = Medio, 3 = Alto, 4 = Crítico)
     * @throws IllegalArgumentException si element es null o priority está fuera de [1..4]
     */
    void enqueue(E element, int priority);

    /**
     * Elimina y retorna el elemento de mayor prioridad.
     * A igual prioridad, retorna el insertado primero (FIFO).
     *
     * @return el elemento de mayor prioridad
     * @throws java.util.NoSuchElementException si la cola está vacía
     */
    E dequeue();

    /**
     * Retorna el elemento de mayor prioridad sin eliminarlo.
     *
     * @return el elemento de mayor prioridad
     * @throws java.util.NoSuchElementException si la cola está vacía
     */
    E peek();

    /**
     * Retorna la prioridad del elemento al frente de la cola.
     *
     * @return la prioridad del primer elemento
     * @throws java.util.NoSuchElementException si la cola está vacía
     */
    int peekPriority();

    /** Elimina todos los elementos. */
    void clear();

    /** Retorna la cantidad de elementos en la cola. */
    int size();

    /** Retorna true si la cola no tiene elementos. */
    boolean isEmpty();
}
