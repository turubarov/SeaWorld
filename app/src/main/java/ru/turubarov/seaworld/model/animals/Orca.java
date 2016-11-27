package ru.turubarov.seaworld.model.animals;

import ru.turubarov.seaworld.model.AnimalMatrix;
import ru.turubarov.seaworld.settings.SettingsOfSeaWorld;

/**
 * Created by Александр on 22.11.2016.
 */

public class Orca extends Animal {
    public int timeAfterEat;

    public Orca(AnimalMatrix matrix) {
        super(matrix);
        timeAfterEat = 0;
    }

    @Override
    public void step() {
        timeAfterReproduction++;
        if (!matrix.searchAndEatFood(this)) {
            move();
            timeAfterEat++;
        } else {
            timeAfterEat = 0;
        }
        if (timeAfterEat > SettingsOfSeaWorld.getInstance().getLiveWithoutEat()) {
            matrix.removeAnimal(this);
        } else {
            if (timeAfterReproduction > timeBetweenReproduction) {
                tryRespoduction();
            }
        }
    }
}
