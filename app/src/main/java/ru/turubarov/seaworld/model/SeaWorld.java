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

    /*
    todo не очень понятно зачем нужно разделение SeaWorld и AnimalMatrix,
         если была идея вынести часть лоики в подкласс, то оня вся туда ушла и AnimalMatrix по факту
         стал "миром", а SeaWorld просто его содержит и выполняет шаги для животных
     */
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
