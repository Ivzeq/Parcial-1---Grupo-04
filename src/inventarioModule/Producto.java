package inventarioModule;

/**
 * Modelo que representa un producto del inventario.
 *
 * Decisión de diseño: el código del producto vive en el modelo además
 * de ser la clave del diccionario. Esto permite que, al recuperar un
 * producto del TDA, siempre sepa cuál es su propio código sin tener
 * que pasarlo como parámetro adicional en cada operación de presentación.
 *
 * Invariantes del modelo (reforzadas en el constructor y setters):
 *   - nombre  : no vacío
 *   - precio  : > 0
 *   - stock   : >= 0  (puede llegar a cero pero no ser negativo)
 */
public class Producto {

    private final String codigo;   // inmutable: el código nunca cambia
    private       String nombre;
    private       double precio;
    private       int    stock;

    // ── Constructor ──────────────────────────────────────────────────────────
    /**
     * @throws IllegalArgumentException si algún campo viola los invariantes
     */
    public Producto(String codigo, String nombre, double precio, int stock) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        setCampos(nombre, precio, stock);   // reutiliza la validación común
        this.codigo = codigo.trim().toUpperCase();
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getCodigo()  { return codigo; }
    public String getNombre()  { return nombre; }
    public double getPrecio()  { return precio; }
    public int    getStock()   { return stock;  }

    // ── Setters con validación ────────────────────────────────────────────────
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException(
                "El precio debe ser mayor a cero. Recibido: " + precio);
        }
        this.precio = precio;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException(
                "El stock no puede ser negativo. Recibido: " + stock);
        }
        this.stock = stock;
    }

    // ── Helper privado ────────────────────────────────────────────────────────
    private void setCampos(String nombre, double precio, int stock) {
        setNombre(nombre);
        setPrecio(precio);
        setStock(stock);
    }

    // ── Presentación ─────────────────────────────────────────────────────────
    /** Línea compacta para listados. */
    @Override
    public String toString() {
        return String.format("%-10s | %-30s | $%10.2f | Stock: %d",
            codigo,
            nombre.length() > 30 ? nombre.substring(0, 27) + "..." : nombre,
            precio,
            stock);
    }

    /** Ficha completa para la vista de detalle. */
    public String toDetalle() {
        String linea = "─".repeat(54);
        return  linea + "\n" +
                String.format("  Código  : %s%n", codigo)          +
                String.format("  Nombre  : %s%n", nombre)          +
                String.format("  Precio  : $%.2f%n", precio)       +
                String.format("  Stock   : %d unidades%n", stock)  +
                linea;
    }
}
