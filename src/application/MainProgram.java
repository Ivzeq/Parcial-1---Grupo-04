package application;

import java.util.Scanner;

public class MainProgram {
    private static boolean running = true;

    public static void run() {

        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.println("Hello and welcome!");

            String exercise = selectExercise(scanner);

            System.out.println("El numero de ejercicio seleccionado es: " + exercise);


            running = false;
        }
    }

    public static String selectExercise (Scanner scanner) {
        Scanner scannerExercise = scanner;

        System.out.println("Ingrese numero de ejercicio");

        String numeroExercise = scannerExercise.nextLine();

        return numeroExercise;

    }
}
