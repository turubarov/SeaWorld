package ru.turubarov.seaworld.data;

import ru.turubarov.seaworld.animals.Animal;
import ru.turubarov.seaworld.animals.Orca;
import ru.turubarov.seaworld.animals.Penguin;

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
                newAnimal = null;
                break;
        }
        return newAnimal;
    }
}

enum AnimalTypes {ORCA, TUX};
