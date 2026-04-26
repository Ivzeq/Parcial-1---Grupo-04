package inventarioModule;

/**
 * TDA Diccionario (mapa clave → valor).
 *
 * Decisión de diseño: el diccionario es la abstracción natural para un
 * inventario porque el acceso central es "dame el producto con código X".
 * Otras estructuras (lista, cola) obligarían a recorrer todos los elementos
 * para cada búsqueda, lo que es ineficiente y conceptualmente incorrecto.
 *
 * Claves nulas o vacías, y valores nulos, son rechazados con excepción,
 * dado que el contrato del TDA exige datos significativos en todo momento.
 * La aplicación es responsable de nunca invocar estas operaciones con
 * datos inválidos; el TDA se defiende como segunda línea.
 *
 * @param <K> tipo de la clave   (debe tener equals bien definido)
 * @param <V> tipo del valor
 */
public interface SimpleDictionary<K, V> {

    /**
     * Asocia {@code key} con {@code value}.
     * Si la clave ya existía, reemplaza el valor anterior y lo retorna.
     * Si la clave es nueva, retorna {@code null}.
     *
     * @throws IllegalArgumentException si key o value son null, o si key
     *         es una cadena vacía/en blanco
     */
    V put(K key, V value);

    /**
     * Retorna el valor asociado a {@code key}, o {@code null} si no existe.
     *
     * @throws IllegalArgumentException si key es null o cadena vacía/en blanco
     */
    V get(K key);

    /**
     * Elimina la entrada con clave {@code key} y retorna su valor,
     * o {@code null} si la clave no existía.
     *
     * @throws IllegalArgumentException si key es null o cadena vacía/en blanco
     */
    V remove(K key);

    /**
     * Indica si existe alguna entrada con la clave dada.
     *
     * @throws IllegalArgumentException si key es null o cadena vacía/en blanco
     */
    boolean containsKey(K key);

    /** Cantidad de entradas en el diccionario. */
    int size();

    /** {@code true} si el diccionario no tiene ninguna entrada. */
    boolean isEmpty();

    /**
     * Retorna todas las claves en un arreglo de Object.
     * El orden no está garantizado.
     * El arreglo tiene longitud {@link #size()}.
     */
    Object[] keys();

    /**
     * Retorna todos los valores en un arreglo de Object.
     * El orden no está garantizado, pero es paralelo al de {@link #keys()}.
     */
    Object[] values();

    /** Elimina todas las entradas. */
    void clear();
}
