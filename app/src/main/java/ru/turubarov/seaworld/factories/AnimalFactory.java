package ru.turubarov.seaworld.factories;

import ru.turubarov.seaworld.enums.AnimalTypes;
import ru.turubarov.seaworld.model.AnimalMatrix;
import ru.turubarov.seaworld.model.animals.Animal;
import ru.turubarov.seaworld.model.animals.Orca;
import ru.turubarov.seaworld.model.animals.Penguin;

/**
 * Created by Александр on 25.11.2016.
 */

public class AnimalFactory {
    private AnimalMatrix matrix;

    public AnimalFactory(AnimalMatrix matrix) {
        this.matrix = matrix;
    }

    public Animal createAnimal(AnimalTypes type) {
        Animal newAnimal;
        switch (type) {
            case ORCA:
                newAnimal = new Orca(this.matrix);
                break;
            case TUX:
                newAnimal = new Penguin(this.matrix);
                break;
            default:
                /*
                TODO factory возврящает null... странный кейс. что знаешь про ecxeptions?
                 */
                newAnimal = null;
                break;
        }
        return newAnimal;
    }
}

;
