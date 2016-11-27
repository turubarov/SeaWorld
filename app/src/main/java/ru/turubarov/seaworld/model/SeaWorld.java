package ru.turubarov.seaworld.model;

import java.util.ArrayList;

import ru.turubarov.seaworld.model.animals.Animal;
import ru.turubarov.seaworld.settings.SettingsOfSeaWorld;

/**
 * Класс, отвечающий за работу с игровым миром
 * Created by Александр on 23.11.2016.
 */

public class SeaWorld implements ISeaWorld {

    private int numOfColumns;
    private int numOfRows;

    private AnimalMatrix animalMatrix;
    private ArrayList<Animal> animalList;

    public SeaWorld() {

        numOfColumns = SettingsOfSeaWorld.getInstance().getNumOfColumns();
        numOfRows = SettingsOfSeaWorld.getInstance().getNumOfRows();

        animalMatrix = new AnimalMatrix(numOfColumns, numOfRows);
        fullSeaWorld();
    }

    @Override
    public Animal[][] getAnimalMatrix() {
        return this.animalMatrix.animals;
    }

    @Override
    public void fullSeaWorld() {
        animalList = animalMatrix.fullMatrix();
    }

    @Override
    public void stepOfSeaWorld() {
        for (Animal animal : animalList) {
            animal.step();
        }
        animalList = animalMatrix.getAnimalList();
    }
}
