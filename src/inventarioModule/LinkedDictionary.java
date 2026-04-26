package inventarioModule;

/**
 * Implementación de {@link SimpleDictionary} mediante lista enlazada de pares.
 *
 * Decisión de diseño: se eligió lista enlazada sobre tabla hash o árbol porque:
 *   - Mantiene coherencia con el enfoque de TDAs propios del proyecto
 *     (lista enlazada ya era conocida por el equipo: SimpleLinkedList,
 *      SimpleLinkedQueue, SimpleLinkedStack, SimpleLinkedSet).
 *   - Para inventarios de comercio pequeños/medianos la diferencia de
 *     rendimiento entre O(n) y O(1) es imperceptible al usuario.
 *   - Evita complejidad extra (función hash, resolución de colisiones).
 *
 * Complejidades:
 *   put / get / remove / containsKey → O(n)
 *   size / isEmpty                   → O(1)
 *   keys / values                    → O(n)
 *
 * @param <K> tipo de la clave
 * @param <V> tipo del valor
 */
public class LinkedDictionary<K, V> implements SimpleDictionary<K, V> {

    // ── Nodo interno ──────────────────────────────────────────────────────────
    private static class Entry<K, V> {
        K       key;
        V       value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key   = key;
            this.value = value;
            this.next  = null;
        }
    }

    // ── Estado ────────────────────────────────────────────────────────────────
    private Entry<K, V> head;
    private int         size;

    public LinkedDictionary() {
        head = null;
        size = 0;
    }

    // ── Operaciones del TDA ───────────────────────────────────────────────────

    @Override
    public V put(K key, V value) {
        validateKey(key);
        if (value == null) {
            throw new IllegalArgumentException("El valor no puede ser null.");
        }

        // Si la clave ya existe, actualizamos y retornamos el valor anterior
        Entry<K, V> existing = findEntry(key);
        if (existing != null) {
            V old = existing.value;
            existing.value = value;
            return old;
        }

        // Clave nueva: insertar al frente (O(1))
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = head;
        head          = newEntry;
        size++;
        return null;
    }

    @Override
    public V get(K key) {
        validateKey(key);
        Entry<K, V> entry = findEntry(key);
        return (entry != null) ? entry.value : null;
    }

    @Override
    public V remove(K key) {
        validateKey(key);

        if (head == null) return null;

        // Caso especial: la clave está en el nodo cabeza
        if (head.key.equals(key)) {
            V old = head.value;
            head  = head.next;
            size--;
            return old;
        }

        // Buscar con puntero al nodo anterior
        Entry<K, V> prev = head;
        while (prev.next != null) {
            if (prev.next.key.equals(key)) {
                V old      = prev.next.value;
                prev.next  = prev.next.next;
                size--;
                return old;
            }
            prev = prev.next;
        }
        return null; // clave no encontrada
    }

    @Override
    public boolean containsKey(K key) {
        validateKey(key);
        return findEntry(key) != null;
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public Object[] keys() {
        Object[] result = new Object[size];
        Entry<K, V> current = head;
        for (int i = 0; i < size; i++) {
            result[i] = current.key;
            current   = current.next;
        }
        return result;
    }

    @Override
    public Object[] values() {
        Object[] result = new Object[size];
        Entry<K, V> current = head;
        for (int i = 0; i < size; i++) {
            result[i] = current.value;
            current   = current.next;
        }
        return result;
    }

    @Override
    public void clear() {
        // Liberar referencias para ayudar al GC
        Entry<K, V> current = head;
        while (current != null) {
            Entry<K, V> next = current.next;
            current.key   = null;
            current.value = null;
            current.next  = null;
            current       = next;
        }
        head = null;
        size = 0;
    }

    // ── Helpers privados ──────────────────────────────────────────────────────

    /** Recorre la lista buscando la entrada con la clave dada. O(n). */
    private Entry<K, V> findEntry(K key) {
        Entry<K, V> current = head;
        while (current != null) {
            if (current.key.equals(key)) return current;
            current = current.next;
        }
        return null;
    }

    /**
     * Valida que la clave no sea null ni, si es String, vacía/en blanco.
     * @throws IllegalArgumentException si la clave es inválida
     */
    private void validateKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("La clave no puede ser null.");
        }
        if (key instanceof String && ((String) key).isBlank()) {
            throw new IllegalArgumentException("La clave no puede estar vacía.");
        }
    }
}
