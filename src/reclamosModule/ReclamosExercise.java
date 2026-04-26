package reclamosModule;

import application.Exercise;

import java.util.Scanner;

/**
 * Aplicación de gestión de reclamos para empresas de atención al consumidor.
 *
 * ══════════════════════════════════════════════════════════════════════
 * DECISIONES DE DISEÑO Y EXPERIENCIA DE USUARIO
 * ══════════════════════════════════════════════════════════════════════
 *
 * 1. ESTRUCTURA DE DATOS CENTRAL
 *    Se utiliza una {@link LinkedPriorityQueue} de {@link Reclamo}.
 *    La cola con prioridad modela fielmente el comportamiento de un
 *    centro de atención: los reclamos más urgentes se atienden primero
 *    independientemente del orden de llegada.  A igual urgencia se
 *    respeta FIFO (orden de llegada), lo que es justo con el cliente.
 *
 * 2. FLUJO DE VISUALIZACIÓN (sin destruir la cola)
 *    Al listar reclamos se extraen TODOS los nodos de la cola, se
 *    presentan al operario uno a uno (de mayor a menor urgencia) y,
 *    al terminar, los no resueltos se re-insertan con su prioridad
 *    original.  Esto garantiza que la cola nunca pierde datos aunque
 *    el operario decida no resolver ninguno.
 *
 *    Alternativa descartada: mantener una lista auxiliar paralela.
 *    Se descartó porque duplicaría el estado y podría causar
 *    inconsistencias; el re-encolado es más limpio con un solo TDA.
 *
 * 3. MANEJO DE INPUTS INVÁLIDOS
 *    Cada lectura de usuario pasa por un método helper (leerTexto,
 *    leerUrgencia, leerSiNo, leerOpcionMenu) que garantiza un valor
 *    válido antes de pasarlo al TDA.  El TDA puede lanzar excepciones,
 *    pero la aplicación las previene con estas validaciones.
 *
 * 4. RESUMEN DE SESIÓN
 *    Al salir se muestra cuántos reclamos se resolvieron vs. quedan
 *    pendientes.  Esto da al operario una visión del trabajo de la
 *    sesión sin necesidad de persistencia entre sesiones.
 *
 * 5. SEPARADORES VISUALES
 *    Se usan líneas de caracteres especiales para delimitar secciones
 *    y mejorar la legibilidad en la consola, compensando la ausencia
 *    de interfaz gráfica.
 * ══════════════════════════════════════════════════════════════════════
 */
public class ReclamosExercise extends Exercise {

    // ── Estado ───────────────────────────────────────────────────────────────
    private final PriorityQueue<Reclamo> colaPendientes;
    private int totalResueltos = 0;   // contador de sesión

    private static final String LINEA_DOBLE = "═".repeat(60);
    private static final String LINEA_FINA  = "─".repeat(60);

    // ── Constructor ──────────────────────────────────────────────────────────
    public ReclamosExercise(Scanner scanner) {
        super(scanner);
        colaPendientes = new LinkedPriorityQueue<>();
    }

    // ── Punto de entrada (heredado de Exercise) ───────────────────────────────
    @Override
    protected void exerciseLogic() {
        mostrarBienvenida();

        boolean active = true;
        while (active) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> redactarReclamo();
                case "2" -> visualizarReclamos();
                case "0" -> {
                    mostrarResumenSesion();
                    System.out.println("Saliendo del sistema de reclamos. ¡Hasta luego!");
                    active = false;
                }
                default  -> System.out.println("  Opción no reconocida. Ingrese 1, 2 o 0.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // OPCIÓN 1 – REDACTAR RECLAMO
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Solicita los datos del nuevo reclamo y lo encola.
     *
     * Decisión: se validan título y descripción con leerTexto() para
     * no encolair un Reclamo con campos vacíos (el constructor del modelo
     * también lo rechazaría, pero la validación previa evita la excepción).
     */
    private void redactarReclamo() {
        System.out.println(LINEA_DOBLE);
        System.out.println("  NUEVO RECLAMO");
        System.out.println(LINEA_DOBLE);

        String titulo      = leerTexto("  Título      : ");
        String descripcion = leerTexto("  Descripción : ");
        Reclamo.Urgencia urgencia = leerUrgencia();

        Reclamo nuevo = new Reclamo(titulo, descripcion, urgencia);
        colaPendientes.enqueue(nuevo, urgencia.getValor());

        System.out.println(LINEA_FINA);
        System.out.printf("  ✓ Reclamo #%03d registrado correctamente.%n", nuevo.getId());
        System.out.println(LINEA_FINA);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // OPCIÓN 2 – VISUALIZAR RECLAMOS
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Muestra los reclamos pendientes en orden de urgencia (mayor primero).
     *
     * Decisión de flujo:
     *   1. Se extraen TODOS los reclamos de la cola en orden de prioridad.
     *   2. Se presentan uno a uno; el operario puede resolver o dejar.
     *   3. Los no resueltos se re-encotan con su prioridad original.
     *
     * Esto respeta el contrato del TDA (no hay "iterador" en la interfaz)
     * y mantiene la consistencia de la estructura.
     */
    private void visualizarReclamos() {
        System.out.println(LINEA_DOBLE);
        System.out.println("  COLA DE RECLAMOS  (orden: mayor urgencia primero)");
        System.out.println(LINEA_DOBLE);

        if (colaPendientes.isEmpty()) {
            System.out.println("  No hay reclamos pendientes. ✓");
            System.out.println(LINEA_FINA);
            return;
        }

        // ── Paso 1: vaciar la cola temporalmente en un arreglo simple ────────
        // Usamos un arreglo de tamaño conocido para no depender de colecciones
        // de Java, coherente con el enfoque de TDAs propios del proyecto.
        int total = colaPendientes.size();
        Reclamo[] buffer = new Reclamo[total];
        for (int i = 0; i < total; i++) {
            buffer[i] = colaPendientes.dequeue();
        }

        // ── Paso 2: mostrar índice rápido ────────────────────────────────────
        System.out.printf("  Total pendientes: %d%n%n", total);
        for (int i = 0; i < total; i++) {
            System.out.printf("  %2d. %s%n", i + 1, buffer[i]);
        }
        System.out.println();

        // ── Paso 3: permitir atender reclamos uno a uno ──────────────────────
        boolean[] resueltos = new boolean[total];

        boolean continuar = true;
        while (continuar) {
            System.out.println(LINEA_FINA);
            System.out.println("  ¿Desea atender algún reclamo?");
            System.out.println("  s - Sí    |    n - No (volver al menú)");
            System.out.print("  Opción: ");
            String resp = leerSiNo();

            if (resp.equals("n")) {
                continuar = false;
            } else {
                atenderReclamo(buffer, resueltos, total);
                // Verificar si quedan pendientes
                boolean hayPendientes = false;
                for (int i = 0; i < total; i++) {
                    if (!resueltos[i]) { hayPendientes = true; break; }
                }
                if (!hayPendientes) {
                    System.out.println("  ✓ Todos los reclamos fueron resueltos.");
                    continuar = false;
                }
            }
        }

        // ── Paso 4: re-encolar los no resueltos ──────────────────────────────
        for (int i = 0; i < total; i++) {
            if (!resueltos[i]) {
                colaPendientes.enqueue(buffer[i], buffer[i].getUrgencia().getValor());
            }
        }

        System.out.println(LINEA_FINA);
    }

    /**
     * Solicita al operario elegir un reclamo por número y marcarlo resuelto.
     *
     * Decisión: se muestra solo la lista de pendientes (no los ya resueltos)
     * para no confundir al operario.  El número ingresado se valida contra
     * los índices disponibles antes de operar.
     */
    private void atenderReclamo(Reclamo[] buffer, boolean[] resueltos, int total) {
        // Mostrar solo los que siguen pendientes
        System.out.println();
        System.out.println("  Reclamos pendientes:");
        boolean hayPendientes = false;
        for (int i = 0; i < total; i++) {
            if (!resueltos[i]) {
                System.out.printf("  %2d. %s%n", i + 1, buffer[i]);
                hayPendientes = true;
            }
        }
        if (!hayPendientes) return;

        System.out.println();
        System.out.print("  Ingrese el número del reclamo a atender: ");
        int eleccion = leerEnteroEnRango(1, total);
        int idx      = eleccion - 1;

        if (resueltos[idx]) {
            System.out.println("  ✗ Ese reclamo ya fue marcado como resuelto.");
            return;
        }

        // Mostrar detalle completo antes de confirmar
        System.out.println();
        System.out.println(buffer[idx].toDetalle());
        System.out.println();
        System.out.println("  ¿Marcar este reclamo como resuelto?");
        System.out.println("  s - Sí (resolver)    |    n - No (dejar en cola)");
        System.out.print("  Opción: ");
        String confirmacion = leerSiNo();

        if (confirmacion.equals("s")) {
            buffer[idx].marcarResuelto();
            resueltos[idx] = true;
            totalResueltos++;
            System.out.printf("  ✓ Reclamo #%03d marcado como resuelto.%n", buffer[idx].getId());
        } else {
            System.out.printf("  El reclamo #%03d se mantiene en la cola.%n", buffer[idx].getId());
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // PRESENTACIÓN
    // ══════════════════════════════════════════════════════════════════════════

    private void mostrarBienvenida() {
        System.out.println(LINEA_DOBLE);
        System.out.println("  SISTEMA DE GESTIÓN DE RECLAMOS");
        System.out.println("  Atención al Consumidor");
        System.out.println(LINEA_DOBLE);
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println(LINEA_FINA);
        System.out.printf("  Pendientes en cola: %d%n", colaPendientes.size());
        if (!colaPendientes.isEmpty()) {
            String urg = colaPendientes.peek().getUrgencia().getEtiqueta().trim();
            System.out.printf("  Próximo a atender : [#%03d] %s  (Urgencia: %s)%n",
                colaPendientes.peek().getId(),
                colaPendientes.peek().getTitulo(),
                urg);
        }
        System.out.println(LINEA_FINA);
        System.out.println("  1. Redactar nuevo reclamo");
        System.out.println("  2. Visualizar / atender reclamos");
        System.out.println("  0. Salir");
        System.out.print("  Opción: ");
    }

    private void mostrarResumenSesion() {
        System.out.println();
        System.out.println(LINEA_DOBLE);
        System.out.println("  RESUMEN DE SESIÓN");
        System.out.println(LINEA_DOBLE);
        System.out.printf("  Reclamos resueltos en esta sesión : %d%n", totalResueltos);
        System.out.printf("  Reclamos pendientes en cola       : %d%n", colaPendientes.size());
        System.out.println(LINEA_DOBLE);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // HELPERS DE LECTURA (garantizan datos válidos antes de llegar al TDA)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Lee un texto no vacío.  Repite hasta obtener al menos un carácter.
     *
     * Decisión: no se acepta whitespace puro como valor válido porque
     * un título o descripción vacíos no tienen sentido de negocio.
     */
    private String leerTexto(String prompt) {
        String valor = "";
        while (valor.isBlank()) {
            System.out.print(prompt);
            valor = scanner.nextLine().trim();
            if (valor.isBlank()) {
                System.out.println("  ✗ El campo no puede estar vacío. Intente nuevamente.");
            }
        }
        return valor;
    }

    /**
     * Solicita el nivel de urgencia y repite hasta recibir una opción válida.
     *
     * Decisión: se muestra el menú completo con número y nombre para que
     * el operario no necesite recordar los códigos de memoria.
     */
    private Reclamo.Urgencia leerUrgencia() {
        Reclamo.Urgencia urgencia = null;
        while (urgencia == null) {
            System.out.println("  Nivel de urgencia:");
            System.out.println("    1 - Bajo");
            System.out.println("    2 - Medio");
            System.out.println("    3 - Alto");
            System.out.println("    4 - Crítico");
            System.out.print("  Opción: ");
            String input = scanner.nextLine().trim();
            try {
                urgencia = Reclamo.Urgencia.desde(input);
            } catch (IllegalArgumentException e) {
                System.out.println("  ✗ " + e.getMessage() +
                    " Use 1, 2, 3, 4 o el nombre de la urgencia.");
            }
        }
        return urgencia;
    }

    /**
     * Lee "s" o "n" (case-insensitive).  Repite ante cualquier otro valor.
     *
     * Decisión: usar solo "s/n" es la convención más reconocible en español
     * para confirmaciones binarias en consola.
     */
    private String leerSiNo() {
        String valor = "";
        while (!valor.equals("s") && !valor.equals("n")) {
            valor = scanner.nextLine().trim().toLowerCase();
            if (!valor.equals("s") && !valor.equals("n")) {
                System.out.print("  ✗ Ingrese 's' o 'n': ");
            }
        }
        return valor;
    }

    /**
     * Lee un entero en el rango [min..max] inclusive.  Repite ante valores
     * fuera de rango o no numéricos.
     *
     * Decisión: capturar NumberFormatException aquí en lugar de dejar que
     * la excepción suba es más amigable y evita un crash visible para el usuario.
     */
    private int leerEnteroEnRango(int min, int max) {
        while (true) {
            String linea = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.printf("  ✗ Ingrese un número entre %d y %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("  ✗ Valor no numérico. Ingrese un número entre %d y %d: ", min, max);
            }
        }
    }
}
