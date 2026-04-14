package application;

import list.ListExercise;
import queueModule.QueueExercise;
import setModule.SetExercise;
import stackModule.StackExercise;

import java.util.Scanner;

public class MainProgram {

    static boolean running = true;
    private static Exercise exercise;
    public static void run() {

        Scanner scanner = new Scanner(System.in);
        while (running) {
            selectExercise(scanner);
            if(exercise != null) exercise.run();

        }
        scanner.close();
    }


    static void selectExercise(Scanner scanner) {
        System.out.println("Opciones a elegir: " +
                "\n 0 - Terminar programa " +
                "\n 1 - Test exercise" +
                "\n 2 - Listas" +
                "\n 3 - Queue" +
                "\n 4 - Stack" +
                "\n 5 - Set");
        String input = scanner.nextLine();
        switch (input) {
            case "0":
                running = false;
                break;
            case "1":
                exercise = new TestExercise(scanner);
                running = false;
                break;
            case "2":
                exercise = new ListExercise(scanner);
                running = false;
                break;
            case "3":
                exercise = new QueueExercise(scanner);
                running = false;
                break;
            case "4":
                exercise = new StackExercise(scanner);
                running = false;
                break;
            case "5":
                exercise = new SetExercise(scanner);
                running = false;
                break;
        }
    }


}
