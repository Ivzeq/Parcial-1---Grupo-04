package setModule;

import application.Exercise;

import java.util.Scanner;

public class SetExercise extends Exercise {
    public SetExercise(Scanner scnr) {
        super(scnr);
    }

    @Override
    protected void exerciseLogic() {
        System.out.println("Bienvenido al ejercicio " +
                "\n mm Main Menu");
        String userInput = scanner.nextLine().toLowerCase();
        if (userInput.equals("mm")) running = false;
    }
}
