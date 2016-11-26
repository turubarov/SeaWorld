package ru.turubarov.seaworld.data;

import android.content.Context;

import java.util.ArrayList;

import ru.turubarov.seaworld.R;
import ru.turubarov.seaworld.animals.Animal;

/**
 * Класс, отвечающий за работу с игровым миром
 * Created by Александр on 23.11.2016.
 */

public class SeaWorld {

    private int numOfColumns;
    private int numOfRows;

    private AnimalMatrix animalMatrix;
    private ArrayList<Animal> animalList;

    private Context context;

    public SeaWorld() {
        this.context = context;

        numOfColumns = Settings.getInstance().getNumOfColumns();
        numOfRows = Settings.getInstance().getNumOfRows();

        animalMatrix = new AnimalMatrix(numOfColumns, numOfRows);
        fullSeaWorld();
    }

    public Animal[][] getAnimalMatrix() {
        return this.animalMatrix.animals;
    }

    public void fullSeaWorld() {
        animalList = animalMatrix.fullMatrix();
    }

    public void step() {
        for (Animal animal : animalList) {
            animal.step();
        }
        animalList = animalMatrix.getAnimalList();
    }
}
