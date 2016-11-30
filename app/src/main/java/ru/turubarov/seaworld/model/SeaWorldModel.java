package ru.turubarov.seaworld.model;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

import ru.turubarov.seaworld.enums.AnimalTypes;
import ru.turubarov.seaworld.factories.AnimalFactory;
import ru.turubarov.seaworld.model.animals.Animal;
import ru.turubarov.seaworld.settings.SettingsOfSeaWorld;

/**
 * Класс хранит ссылки на всех животных в двумерной матрице
 * Необходим для ускорения поиска соседей и свободных мест для перемещения
 * Created by Александр on 25.11.2016.
 */

public class SeaWorldModel implements ISeaWorldModel {

    private AnimalFactory animalFactory;
    public Animal[][] animals;

    private int numOfColumns;
    private int numOfRows;

    private ArrayList<Animal> animalList;

    public SeaWorldModel(int numOfColumns, int numOfRows) {
        this.numOfColumns = numOfColumns;
        this.numOfRows = numOfRows;

        animals = new Animal[this.numOfColumns][this.numOfRows];
        animalFactory = new AnimalFactory(this);

        animalList = fullMatrix();
    }

    public AnimalFactory getAnimalFactory() {
        return animalFactory;
    }

    public ArrayList<Animal> fullMatrix() {
        /*
        todo есть идеи, как расставить в случайным образом за один обход, а не за 2 как сейчас?
        да, есть. например, нам нужно расставить 75 животных на поле 10x15.
        для каждого животного делаем следующее: генерируем два случайных числа
        (например, x от 0 до 10 и y от 0 до 15), и проверяем значение animals[x][y].
         если оно равно null, создаём животное и записываем в элемент animals[x][y].
         если нет - снова генерируем пару из двух чисел.
         минус данного метода в том, что при большом количестве животных он может работать
         довольно долго (низка вероятность однаружения свободной клетки).
         он хорошо подойдёт для большого поля с низой плотностью заполнения
          метод, реализованный мной, работает за одинаковое время при любой плотности животных
         */

        double shareOfPenguin = (double)SettingsOfSeaWorld.getInstance().getPercentOfPenguin() / 100;
        double shareOfOrca = (double)SettingsOfSeaWorld.getInstance().getPercentOfOrca() / 100;
        int maxTuxIndex = (int)(numOfColumns * numOfRows * shareOfPenguin);
        int maxOrcaIndex = (int)(maxTuxIndex + numOfColumns * numOfRows * shareOfOrca);

        // заполняем матрицу с животными по порядку

        for (int i = 0; i < numOfColumns; i++) {
            for (int j = 0; j < numOfRows; j++) {
                try {
                    if (numOfRows * i + j < maxTuxIndex)
                        animals[i][j] = animalFactory.createAnimal(AnimalTypes.TUX);
                    else if (numOfRows * i + j >= maxTuxIndex && numOfRows * i + j < maxOrcaIndex)
                        animals[i][j] = animalFactory.createAnimal(AnimalTypes.ORCA);
                    else
                        animals[i][j] = null;
                } catch (Exception e) {
                    animals[i][j] = null;
                }
            }
        }

        // перемешиваем матрицу в случайном порядке
        int randX, randY;
        Random rand = new Random();
        Animal tmp;
        for (int i = 0; i < numOfColumns; i++) {
            for (int j = 0; j < numOfRows; j++) {
                randX = rand.nextInt(numOfColumns);
                randY = rand.nextInt(numOfRows);
                tmp = animals[i][j];
                animals[i][j] = animals[randX][randY];
                animals[randX][randY] = tmp;
            }
        }

        return getAnimalList();
    }

    public ArrayList<Animal> getAnimalList() {
        animalList = new ArrayList<Animal>();
        // обходим всю матрицу и находим всех несъеденных животных
        for (int i = 0; i < numOfColumns; i++) {
            for (int j = 0; j < numOfRows; j++) {
                if (animals[i][j] != null && !animals[i][j].isDead) {
                    animals[i][j].setPosition(i, j);
                    animalList.add(animals[i][j]);
                }
                if (animals[i][j] != null && animals[i][j].isDead) {
                    animals[i][j] = null;
                }
            }
        }
        return this.animalList;
    }

    public boolean removeAnimal(Animal animal) {
        int curX = animal.position.x;
        int curY = animal.position.y;
        boolean result;
        if (animals[curX][curY] != null) {
            animals[curX][curY] = null;
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public boolean moveAnimal(Animal animal, Point newPosition, boolean checkFree) {
        int newX = newPosition.x;
        int newY = newPosition.y;
        int curX = animal.position.x;
        int curY = animal.position.y;
        boolean result;

        // проверяем, свободно ли место для перемещения
        if (animals[newX][newY] == null || !checkFree) { // если да, перемещаем
            animals[newX][newY] = animal;
            animals[curX][curY] = null;
            animal.setPosition(newX, newY);
            result = true;
        } else { // если нет, ничего не делаем
            result = false;
        }

        return result;
    }

    /*
    todo наличие логики движения живатных приведет к сильному разростанию класса при увеличении числа видов
    согласен. перенёс логику в класс животного
     */

    public Point searchPositionForChild(Animal animal) {
        int curX = animal.position.x;
        int curY = animal.position.y;
        // ищем свободное место для ребёнка
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((i != 1 || j != 1)
                        && curX + i - 1 >= 0 && curX + i - 1 < numOfColumns
                        && curY + j - 1 >= 0 && curY + j - 1 < numOfRows) {
                    if (animals[curX + i - 1][curY + j - 1] == null) {
                        return new Point(curX + i - 1, curY + j - 1);
                    }
                }
            }
        }
        return null;
    }

    public boolean putAnimal(Animal animal, Point position) {
        boolean result;
        animal.setPosition(position.x, position.y);
        if (animals[position.x][position.y] == null) {
            animals[position.x][position.y] = animal;
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public Animal getAnimal(int x, int y) {
        return animals[x][y];
    }

    public boolean hitPointOnRange(int x, int y) {
        return (x >= 0 && x < numOfColumns
                && y >= 0 && y  < numOfRows);
    }

    @Override
    public void fullSeaWorld() {
        animalList = fullMatrix();
    }

    @Override
    public void stepOfSeaWorld() {
        for (Animal animal : animalList) {
            animal.step();
        }
        animalList = getAnimalList();
    }

    @Override
    public Animal[][] getAnimalMatrix() {
        return animals;
    }
}
