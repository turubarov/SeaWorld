package ru.turubarov.seaworld.factories;

import ru.turubarov.seaworld.enums.AnimalTypes;
import ru.turubarov.seaworld.model.SeaWorldModel;
import ru.turubarov.seaworld.model.animals.Animal;
import ru.turubarov.seaworld.model.animals.Orca;
import ru.turubarov.seaworld.model.animals.Penguin;

/**
 * Created by Александр on 25.11.2016.
 */

public class AnimalFactory {
    private SeaWorldModel matrix;

    public AnimalFactory(SeaWorldModel matrix) {
        this.matrix = matrix;
    }

    public Animal createAnimal(AnimalTypes type) throws Exception {
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
                Переделал. Если тип животного неизвестен, выкидываю исключение
                 */
                throw new Exception("Unknown type of animal");
        }
        return newAnimal;
    }
}

;
