package list;

import application.Exercise;

import listModule.SimpleArrayList;
import listModule.SimpleList;
import java.util.Objects;
import java.util.Scanner;

public class ListExercise extends Exercise {

    private int currentPhase = 0;
    private SimpleList<String> list;

    public ListExercise(Scanner scnr) {
        super(scnr);
        list = new SimpleArrayList<>();
    }


    @Override
    protected void exerciseLogic() {
        switch (currentPhase) {
            case 0:
                menuLogic();
                break;
            case 1:
                addElement();
                break;
            case 2:
                removeElementByIndex();
                break;
            case 3:
                removeElementByReference();
                break;
            case 4:
                clear();
                break;
        }
    }

    private void menuLogic() {
        System.out.println("Bienvenido " +
                "\n su lista actual es: " + list.toString() +
                "\n A- Agregar elemento a la lista" +
                "\n B- remover elemento por índice" +
                "\n C- Remover por referencia" +
                "\n D- clear" +
                "\n mm- Main Menu"
        );
        String userInput = scanner.nextLine().toUpperCase();
        switch (userInput) {
            case "A":
                currentPhase = 1;
                exerciseLogic();
                break;
            case "B":
                currentPhase = 2;
                exerciseLogic();
                break;
            case "C":
                currentPhase = 3;
                exerciseLogic();
                break;
            case "D":
                currentPhase = 4;
                exerciseLogic();
                break;
            case "MM":
                running = true;
                break;
            default:
                System.out.println("ingrese otro input");
                menuLogic();
        }
    }

    private void addElement() {
        System.out.println("Cargue nuevo elemento");
        String newValue = scanner.nextLine().toUpperCase();
        list.add(newValue);
        boolean runFunction = false;
        while (!runFunction) {
            System.out.println(list);
            System.out.println("Desea agregar otro elemento? y/otro valor");
            String userInput = scanner.nextLine().toLowerCase();
            if (userInput.equals("y")) {
                System.out.println("Cargue el nuevo elemento");
                newValue = scanner.nextLine().toUpperCase();
                list.add(newValue);
            } else {
                runFunction = true;
                currentPhase = 0;
                menuLogic();
            }
        }
    }

    private void removeElementByIndex() {
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println("Lista: " + list);
        System.out.println("Ingrese el index del valor a borrar: ");
        int index = Integer.parseInt(scanner.nextLine());
        list.remove(index);
        boolean runFunction = false;
        while (!runFunction) {
            System.out.println(list);
            System.out.println("Desea borrar otro elemento? y/otro valor");
            String userInput = scanner.nextLine().toLowerCase();
            if (userInput.equals("y")) {
                System.out.println("cargue el elemento a borrar:");
                index = Integer.parseInt(scanner.nextLine());
                list.remove(index);
            } else {
                runFunction = true;
                currentPhase = 0;
                menuLogic();
            }
        }
    }

    private void removeElementByReference() {
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println("Lista: " + list);
        String newValue = scanner.nextLine().toUpperCase();
        list.remove(newValue);
        boolean runFunction = false;
        while (!runFunction) {
            System.out.println(list);
            System.out.println("Desea borrar otro elemento? y/otro valor");
            String userInput = scanner.nextLine().toLowerCase();
            if (userInput.equals("y")) {
                System.out.println("cargue el elemento a boorar:");
                newValue = scanner.nextLine().toUpperCase();
                list.remove(newValue);
            } else {
                runFunction = true;
                currentPhase = 0;
                menuLogic();
            }
        }

        System.out.println("Indique el elemento de referencia");
        String reference = scanner.nextLine().toUpperCase();
        list.remove(reference);
    }

    private void clear() {
        System.out.println("Seguro? y/any");
        String answer = scanner.nextLine().toUpperCase();
        if (answer.equals("y")) {
            list.clear();
        } else {
            currentPhase = 0;
            menuLogic();
        }
    }


}
