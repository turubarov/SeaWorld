package ru.turubarov.seaworld.model;

import ru.turubarov.seaworld.model.animals.Animal;

/**
 * Created by Александр on 27.11.2016.
 */

public interface ISeaWorldModel {
    public void fullSeaWorld();
    public void stepOfSeaWorld();
    public Animal[][] getAnimalMatrix();
}
