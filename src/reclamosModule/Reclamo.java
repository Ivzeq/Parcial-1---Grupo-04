package reclamosModule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Modelo que representa un reclamo registrado por un operario.
 *
 * Decisión de diseño: Se incluye un timestamp de creación para ofrecer
 * trazabilidad (el operario puede ver cuándo se registró el reclamo).
 * El estado "resuelto" vive dentro del modelo para poder generar un
 * resumen completo al finalizar la sesión sin necesidad de una estructura
 * auxiliar externa.
 */
public class Reclamo {

    // ── Enumerado de urgencia ────────────────────────────────────────────────
    public enum Urgencia {
        BAJO    (1, "Bajo    "),
        MEDIO   (2, "Medio   "),
        ALTO    (3, "Alto    "),
        CRITICO (4, "Crítico ");

        private final int    valor;
        private final String etiqueta;

        Urgencia(int valor, String etiqueta) {
            this.valor    = valor;
            this.etiqueta = etiqueta;
        }

        public int    getValor()    { return valor; }
        public String getEtiqueta() { return etiqueta; }

        /**
         * Parsea un texto ingresado por el usuario (número o nombre).
         * Acepta: "1","bajo" / "2","medio" / "3","alto" / "4","critico","crítico"
         *
         * @throws IllegalArgumentException si el texto no corresponde a ninguna urgencia
         */
        public static Urgencia desde(String texto) {
            if (texto == null || texto.isBlank()) {
                throw new IllegalArgumentException("La urgencia no puede estar vacía.");
            }
            switch (texto.trim().toLowerCase()) {
                case "1": case "bajo":              return BAJO;
                case "2": case "medio":             return MEDIO;
                case "3": case "alto":              return ALTO;
                case "4": case "critico": case "crítico": return CRITICO;
                default:
                    throw new IllegalArgumentException(
                        "Urgencia inválida: \"" + texto + "\".");
            }
        }
    }

    // ── Estado ───────────────────────────────────────────────────────────────
    private static final DateTimeFormatter FMT =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static int contadorId = 1;

    private final int           id;
    private final String        titulo;
    private final String        descripcion;
    private final Urgencia      urgencia;
    private final LocalDateTime fechaCreacion;
    private       boolean       resuelto;

    // ── Constructor ──────────────────────────────────────────────────────────
    /**
     * @throws IllegalArgumentException si título, descripción o urgencia son inválidos
     */
    public Reclamo(String titulo, String descripcion, Urgencia urgencia) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        if (urgencia == null) {
            throw new IllegalArgumentException("La urgencia no puede ser null.");
        }
        this.id            = contadorId++;
        this.titulo        = titulo.trim();
        this.descripcion   = descripcion.trim();
        this.urgencia      = urgencia;
        this.fechaCreacion = LocalDateTime.now();
        this.resuelto      = false;
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public int      getId()          { return id; }
    public String   getTitulo()      { return titulo; }
    public String   getDescripcion() { return descripcion; }
    public Urgencia getUrgencia()    { return urgencia; }
    public boolean  isResuelto()     { return resuelto; }

    public String getFechaFormateada() {
        return fechaCreacion.format(FMT);
    }

    // ── Mutador ───────────────────────────────────────────────────────────────
    public void marcarResuelto() { this.resuelto = true; }

    // ── Presentación ─────────────────────────────────────────────────────────
    /** Línea compacta para la lista de la cola. */
    @Override
    public String toString() {
        return String.format("[#%03d] %-40s | %s | %s",
            id,
            titulo.length() > 40 ? titulo.substring(0, 37) + "..." : titulo,
            urgencia.getEtiqueta(),
            getFechaFormateada());
    }

    /** Vista detallada para cuando el operario atiende el reclamo. */
    public String toDetalle() {
        String linea = "─".repeat(58);
        return  linea + "\n" +
                String.format("  Reclamo #%03d   |   Urgencia: %s   |   %s%n",
                    id, urgencia.getEtiqueta().trim(), getFechaFormateada()) +
                linea + "\n" +
                "  Título      : " + titulo + "\n" +
                "  Descripción : " + descripcion + "\n" +
                "  Estado      : " + (resuelto ? "✓ Resuelto" : "⏳ Pendiente") + "\n" +
                linea;
    }
}
