package ru.turubarov.seaworld.model;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

import ru.turubarov.seaworld.enums.AnimalTypes;
import ru.turubarov.seaworld.factories.AnimalFactory;
import ru.turubarov.seaworld.model.animals.Animal;
import ru.turubarov.seaworld.model.animals.Orca;
import ru.turubarov.seaworld.model.animals.Penguin;

/**
 * Класс хранит ссылки на всех животных в двумерной матрице
 * Необходим для ускорения поиска соседей и свободных мест для перемещения
 * Created by Александр on 25.11.2016.
 */

public class AnimalMatrix {

    private AnimalFactory animalFactory;
    public Animal[][] animals;

    private int numOfColumns;
    private int numOfRows;

    private ArrayList<Animal> animalList;

    public AnimalMatrix(int numOfColumns, int numOfRows) {
        this.numOfColumns = numOfColumns;
        this.numOfRows = numOfRows;

        animals = new Animal[this.numOfColumns][this.numOfRows];
        animalFactory = new AnimalFactory(this);

        animalList = new ArrayList<Animal>();
    }

    public ArrayList<Animal> fullMatrix() {
        /*
        todo есть идеи, как расставить в случайным образом за один обход, а не за 2 как сейчас?
         */

        int maxTuxIndex = (int)(numOfColumns * numOfRows * 0.5);
        int maxOrcaIndex = (int)(maxTuxIndex + numOfColumns * numOfRows * 0.05);

        // заполняем матрицу с животными по порядку
        for (int i = 0; i < numOfColumns; i++) {
            for (int j = 0; j < numOfRows; j++) {
                if (numOfRows * i + j < maxTuxIndex)
                    animals[i][j] = animalFactory.createAnimal(AnimalTypes.TUX);
                else if (numOfRows * i + j >= maxTuxIndex && numOfRows * i + j < maxOrcaIndex)
                    animals[i][j] = animalFactory.createAnimal(AnimalTypes.ORCA);
                else
                    animals[i][j] = null;
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
        animalList.clear();
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

    public boolean moveAnimal(Animal animal, Point newPosition) {
        int newX = newPosition.x;
        int newY = newPosition.y;
        int curX = animal.position.x;
        int curY = animal.position.y;
        boolean result;

        // проверяем, свободно ли место для перемещения
        if (animals[newX][newY] == null) { // если да, перемещаем
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
     */
    public Penguin searchFood(Orca orca) {
        int curX = orca.position.x;
        int curY = orca.position.y;
        ArrayList<Penguin> foods = new ArrayList<Penguin>();
        Random rand = new Random();
        // в цикле ищем пингвина на съедение неподалёку от косатки orca
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                if ((j != 1 || k != 1)
                        && curX + j - 1 >= 0 && curX + j - 1 < numOfColumns
                        && curY + k - 1 >= 0 && curY + k - 1 < numOfRows) {
                    if (animals[curX + j - 1][curY + k - 1] instanceof Penguin) {
                        foods.add((Penguin)animals[curX + j - 1][curY + k - 1]);
                    }
                }
            }
        }
        // если пингвины неподалёку найдены, выбираем случайного
        if (foods.size() > 0) {
            return foods.get(rand.nextInt(foods.size()));
        }
        return null;
    }

    public boolean searchAndEatFood(Orca orca) {
        int newX;
        int newY;
        int curX = orca.position.x;
        int curY = orca.position.y;
        boolean result;
        Penguin tuxForEat = searchFood(orca);
        // перемещаем касатку на место съеденного пингвина
        if (tuxForEat != null) {
            newX = tuxForEat.position.x;
            newY = tuxForEat.position.y;
            animals[newX][newY] = orca;
            animals[curX][curY] = null;
            tuxForEat.setPosition(curX, curY);
            orca.setPosition(newX, newY);
            tuxForEat.isDead = true;
            result = true;
        } else { // если кушать нечего, то ничего не делаем
            result = false;
        }
        return result;
    }

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
        if (animals[position.x][position.y] == null) {
            animals[position.x][position.y] = animal;
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
