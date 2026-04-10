package stackModule;

import application.Exercise;
import java.util.Scanner;

/**
 * Interactive console exercise for {@link SimpleStack}.
 *
 * <p>Extends {@link application.Exercise}. Todo el ciclo interactivo vive
 * dentro de {@link #exerciseLogic()}, que corre su propio loop de menú hasta
 * que el usuario elige salir.</p>
 *
 * <p>Reglas del menú:
 * <ul>
 *   <li><b>push</b>  – repetible.</li>
 *   <li><b>pop</b>   – repetible; verifica que la pila no esté vacía primero.</li>
 *   <li><b>peek</b>  – NO repetible (mismo resultado siempre); vuelve al menú.</li>
 *   <li><b>clear</b> – solo llama a {@code clear()} si la pila no está vacía.</li>
 * </ul>
 */
public class StackExercise extends Exercise {

    // -----------------------------------------------------------------------
    // State
    // -----------------------------------------------------------------------

    private final SimpleStack<String> stack;
    private boolean firstTime = true;

    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    public StackExercise(SimpleStack<String> stack, Scanner scanner) {
        super(scanner);
        this.stack = stack;
    }

    // -----------------------------------------------------------------------
    // Exercise contract
    // -----------------------------------------------------------------------

    /**
     * Corre la sesión interactiva completa.
     * Es llamado una sola vez por {@link application.Exercise#run()}.
     */
    @Override
    protected void exerciseLogic() {
        boolean active = true;
        while (active) {
            if (firstTime) {
                printWelcome();
                firstTime = false;
            } else {
                printStatus();
            }

            printMenu();
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" -> doPush();
                case "2" -> doPop();
                case "3" -> doPeek();
                case "4" -> doClear();
                case "0" -> {
                    System.out.println("Saliendo del ejercicio de Pila. ¡Hasta luego!");
                    active = false;
                }
                default -> System.out.println("  Opción no reconocida. Intente nuevamente.");
            }
        }
    }

    // -----------------------------------------------------------------------
    // Display helpers
    // -----------------------------------------------------------------------

    private void printWelcome() {
        separator();
        System.out.println("  Bienvenido al ejercicio de Stack (Pila)");
        System.out.println("  Implementación: " + stack.getClass().getSimpleName());
        separator();
    }

    private void printStatus() {
        separator();
        System.out.println("  Estado de la pila");
        System.out.println("  isEmpty : " + stack.isEmpty());
        System.out.println("  size    : " + stack.size());
        separator();
    }

    private void printMenu() {
        System.out.println("Seleccione una operación:");
        System.out.println("  1. push  (insertar elemento)");
        System.out.println("  2. pop   (remover tope)");
        System.out.println("  3. peek  (ver tope sin remover)");
        System.out.println("  4. clear (vaciar la pila)");
        System.out.println("  0. Salir");
        System.out.print("Opción: ");
    }

    private void separator() {
        System.out.println("----------------------------------------");
    }

    // -----------------------------------------------------------------------
    // Operations
    // -----------------------------------------------------------------------

    /** Push – repetible. */
    private void doPush() {
        boolean repeat = true;
        while (repeat) {
            System.out.print("Elemento a pushear: ");
            String element = scanner.nextLine().trim();
            if (!element.isEmpty()) {
                stack.push(element);
                System.out.println("  → Pusheado: \"" + element + "\"");
                System.out.println("  → Pila ahora: " + stack);
            }
            System.out.print("¿Repetir push? (s/n): ");
            repeat = scanner.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    /** Pop – repetible; verifica vacío antes de entrar y en cada iteración. */
    private void doPop() {
        if (stack.isEmpty()) {
            System.out.println("  ✗ La pila está vacía, no se puede hacer pop.");
            return;
        }
        boolean repeat = true;
        while (repeat) {
            if (stack.isEmpty()) {
                System.out.println("  ✗ La pila está vacía, no se puede continuar.");
                break;
            }
            String removed = stack.pop();
            System.out.println("  → Pop devolvió: \"" + removed + "\"");
            System.out.println("  → Pila ahora: " + stack);
            System.out.print("¿Repetir pop? (s/n): ");
            repeat = scanner.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    /** Peek – NO repetible; vuelve al menú principal automáticamente. */
    private void doPeek() {
        if (stack.isEmpty()) {
            System.out.println("  ✗ La pila está vacía, no se puede hacer peek.");
            return;
        }
        System.out.println("  → Tope de la pila: \"" + stack.peek() + "\"");
    }

    /** Clear – solo ejecuta si la pila tiene elementos. */
    private void doClear() {
        if (stack.isEmpty()) {
            System.out.println("  ✗ La pila ya está vacía, no es necesario limpiarla.");
            return;
        }
        stack.clear();
        System.out.println("  → Pila vaciada correctamente.");
    }

    // -----------------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------------

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("¿Qué implementación desea usar?");
        System.out.println("  1. SimpleArrayStack  (respaldada por array)");
        System.out.println("  2. SimpleLinkedStack (nodos enlazados)");
        System.out.print("Opción: ");
        String choice = sc.nextLine().trim();

        SimpleStack<String> stack = choice.equals("2")
                ? new SimpleLinkedStack<>()
                : new SimpleArrayStack<>();

        new StackExercise(stack, sc).run();
    }
}