package application;

import java.util.Scanner;

public abstract class Exercise {

    protected boolean running = true;
    protected Scanner scanner;

    public Exercise(Scanner scnr) {
        scanner = scnr;
    }

    public void run() {
        while (running) {
            exerciseLogic();
            running = false;
        }
    }

    protected abstract void exerciseLogic();
}
