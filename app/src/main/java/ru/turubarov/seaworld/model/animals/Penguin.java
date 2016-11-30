package ru.turubarov.seaworld.model.animals;

import ru.turubarov.seaworld.R;
import ru.turubarov.seaworld.enums.AnimalTypes;
import ru.turubarov.seaworld.model.SeaWorldModel;
import ru.turubarov.seaworld.settings.SettingsOfSeaWorld;

/**
 * Created by Александр on 22.11.2016.
 */

public class Penguin  extends Animal {
    public Penguin(SeaWorldModel matrix) {
        super(matrix);
        timeBetweenReproduction = SettingsOfSeaWorld.getInstance().getReproductionRateOfPenguin();
    }

    @Override
    public Animal getChild() {
        try {
            return matrix.getAnimalFactory().createAnimal(AnimalTypes.TUX);
        }
        catch (Exception e) {
            return null;
        }
    }

    public int getDrawableResourceId() {
        return R.drawable.tux;
    }
}
