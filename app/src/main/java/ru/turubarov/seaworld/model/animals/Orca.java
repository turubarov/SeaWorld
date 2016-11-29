package ru.turubarov.seaworld.model.animals;

import ru.turubarov.seaworld.R;
import ru.turubarov.seaworld.enums.AnimalTypes;
import ru.turubarov.seaworld.model.AnimalMatrix;
import ru.turubarov.seaworld.settings.SettingsOfSeaWorld;

/**
 * Created by Александр on 22.11.2016.
 */

public class Orca extends Animal {

    /*
    todo вопрос: модификаторы доступа
    исправил
     */
    private int timeAfterEat;

    public Orca(AnimalMatrix matrix) {
        super(matrix);
        timeBetweenReproduction = SettingsOfSeaWorld.getInstance().getReproductionRateOfOrca();
        timeAfterEat = 0;
    }

    @Override
    public int getDrawableResourceId() {
        return R.drawable.orca;
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

    @Override
    public Animal getChild() {
        try {
            return matrix.getAnimalFactory().createAnimal(AnimalTypes.ORCA);
        }
        catch (Exception e) {
            return null;
        }
    }
}
