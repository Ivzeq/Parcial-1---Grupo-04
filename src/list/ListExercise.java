package list;

import application.Exercise;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListExercise extends Exercise {

    private int currentPhase = 0;
    private List<String> list;

    public ListExercise(Scanner scnr) {
        super(scnr);
        list = new ArrayList<>();
    }


    @Override
    protected void exerciseLogic() {
        switch (currentPhase){
            case 0:
                System.out.println(list.stream().toList());
                System.out.println("Bienvenido " +
                        "\n su lista actual es: " + list.toString() +
                        "\n A- Agregar elemento a la lista" +
                        "\n B- remover elemento por índice" +
                        "\n C- Remover por referencia" +
                        "\n D- clear");
                break;
            case 1:



        }
        menuLogic();
        scanner.close();
    }


    private void menuLogic(){
        String userInput = scanner.nextLine().toUpperCase();
        switch (userInput) {
            case "A":
                addElement();
                break;
            case "B":
                removeElementByIndex();
                break;
            case "C":
                removeElementByReference();
                break;
            case "D":
                clear();
                break;

        }
    }

    private void addElement() {
        System.out.println("Cargue nuevo elemento");
        String newValue = scanner.nextLine().toUpperCase();
        list.add(newValue);
    }

    private void removeElementByIndex() {
        System.out.println("Indique el índice de referencia");
        String index = scanner.nextLine().toUpperCase();
        list.remove(index);
    }
    private void removeElementByReference() {
        System.out.println("Indique el elemento de referencia");
        String reference = scanner.nextLine().toUpperCase();
        list.remove(reference);
    }
    private void clear() {
        list.clear();
    }



}
