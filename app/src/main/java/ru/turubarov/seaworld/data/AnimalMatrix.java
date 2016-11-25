package ru.turubarov.seaworld.data;

import android.graphics.Point;

import ru.turubarov.seaworld.animals.Animal;

/**
 * Класс хранит ссылки на всех животных в двумерной матрице
 * Необходим для ускорения поиска соседей и свободных мест для перемещения
 * Created by Александр on 25.11.2016.
 */

public class AnimalMatrix {
    private Animal[][] animals;

    public AnimalMatrix() {
        animals = new Animal[15][10];
    }

    public boolean putAnimal(Animal animal, Point position) {
        boolean result;
        if (animals[position.x][position.y] == null) {
            animals[position.x][position.y] = animal;
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
