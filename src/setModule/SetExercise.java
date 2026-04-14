package setModule;

import application.Exercise;
import java.util.Scanner;

public class SetExercise extends Exercise {

    private SimpleSet<String> setA;
    private SimpleSet<String> setB;
    private boolean firstTime = true;

    public SetExercise(Scanner scanner) {
        super(scanner);
        setA = new SimpleArraySet<>();
        setB = new SimpleArraySet<>();
    }

    @Override
    protected void exerciseLogic() {
        // Choose implementation once
        String choice = "";
        while (!(choice.equals("1") || choice.equals("2"))) {
            System.out.println("¿Qué implementación desea usar?");
            System.out.println("  1. SimpleArraySet");
            System.out.println("  2. SimpleLinkedSet");
            choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                setA = new SimpleArraySet<>();
                setB = new SimpleArraySet<>();
            } else if (choice.equals("2")) {
                setA = new SimpleLinkedSet<>();
                setB = new SimpleLinkedSet<>();
            } else {
                System.out.println("Opción inválida, ingrese una correcta.");
            }
        }

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
                case "1" -> doOperation(setA, "A");
                case "2" -> doOperation(setB, "B");
                case "3" -> printResult("Unión (A ∪ B)", setA.unionWith(setB));
                case "4" -> printResult("Intersección (A ∩ B)", setA.intersectWith(setB));
                case "5" -> printResult("Diferencia (A - B)", setA.differenceWith(setB));
                case "6" -> printResult("Diferencia (B - A)", setB.differenceWith(setA));
                case "0" -> {
                    System.out.println("Saliendo del ejercicio de Set. ¡Hasta luego!");
                    active = false;
                }
                default -> System.out.println("  Opción no reconocida. Intente nuevamente.");
            }
        }
    }

    private void doOperation(SimpleSet<String> set, String name) {
        String opChoice = "";
        while (!(opChoice.equals("1") || opChoice.equals("2"))) {
            System.out.println("  ¿Qué desea hacer sobre Set " + name + "?");
            System.out.println("  1. Agregar elemento");
            System.out.println("  2. Remover elemento");
            opChoice = scanner.nextLine().trim();
            if (!opChoice.equals("1") && !opChoice.equals("2")) {
                System.out.println("  Opción inválida.");
            }
        }

        boolean repeat = true;
        while (repeat) {
            if (opChoice.equals("1")) {
                System.out.print("  Elemento a agregar: ");
                String element = scanner.nextLine().trim().toUpperCase();
                boolean success = set.add(element);
                System.out.println(success
                        ? "  → \"" + element + "\" agregado correctamente."
                        : "  ✗ \"" + element + "\" ya existe en el Set.");
            } else {
                System.out.print("  Elemento a remover: ");
                String element = scanner.nextLine().trim().toUpperCase();
                boolean success = set.remove(element);
                System.out.println(success
                        ? "  → \"" + element + "\" removido correctamente."
                        : "  ✗ \"" + element + "\" no existe en el Set.");
            }
            System.out.print("  ¿Repetir operación? (s/n): ");
            repeat = scanner.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    private void printResult(String label, SimpleSet<String> result) {
        separator();
        System.out.println("  " + label + ": " + result);
        separator();
    }

    private void printWelcome() {
        separator();
        System.out.println("  Bienvenido al ejercicio de Set (Conjunto)");
        separator();
    }

    private void printStatus() {
        separator();
        System.out.println("  Set A → " + setA + "  |  size: " + setA.size() + "  |  isEmpty: " + setA.isEmpty());
        System.out.println("  Set B → " + setB + "  |  size: " + setB.size() + "  |  isEmpty: " + setB.isEmpty());
        separator();
    }

    private void printMenu() {
        System.out.println("Seleccione una operación:");
        System.out.println("  1. Operar sobre Set A");
        System.out.println("  2. Operar sobre Set B");
        System.out.println("  3. Unión        (A ∪ B)");
        System.out.println("  4. Intersección (A ∩ B)");
        System.out.println("  5. Diferencia   (A - B)");
        System.out.println("  6. Diferencia   (B - A)");
        System.out.println("  0. Salir");
        System.out.print("Opción: ");
    }

    private void separator() {
        System.out.println("----------------------------------------");
    }
}