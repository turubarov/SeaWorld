package ru.turubarov.seaworld.model.animals;

import android.graphics.Point;
import java.util.Random;

import ru.turubarov.seaworld.model.AnimalMatrix;
import ru.turubarov.seaworld.settings.SettingsOfSeaWorld;

/**
 * Created by Александр on 22.11.2016.
 */

public class Animal {

    public Point position;
    protected int timeAfterReproduction;
    protected int timeBetweenReproduction;

    AnimalMatrix matrix;
    /*
    todo у каждого Animal свой личный рандом... а нужно ли?
     */
    Random rand;

    public boolean isDead;

    public Animal(AnimalMatrix matrix) {
        this.matrix = matrix;
        position = new Point();
         timeAfterReproduction = 0;
        rand = new Random();
        isDead = false;

        /*
        todo чтоб такого кода не было, можно воспользовать наследованием. соответственно, вопрос: как?
         */
        if (this instanceof Penguin) {
            timeBetweenReproduction = SettingsOfSeaWorld.getInstance().getReproductionRateOfPenguin();
        } else if (this instanceof Orca) {
            timeBetweenReproduction = SettingsOfSeaWorld.getInstance().getReproductionRateOfOrca();
        }
    }

    public void setPosition(int x, int y) {
        position.set(x, y);
    }

    public void move() {
        int offsetX, offsetY;
        Point newPosition;
        /*
        todo "magic numbers"... много "magic numbers"
         */
        do {
            offsetX = rand.nextInt(3) - 1;
            offsetY = rand.nextInt(3) - 1;
            /*
            todo походит на range check, по идее эта логика должна быть в классе,
                 реализующем среду, в которой водятся животные, а не в самом животном
             */
        } while ((offsetX == 0 && offsetY == 0)
                || ((position.x + offsetX) < 0)
                || ((position.x + offsetX) >= 10)
                || ((position.y + offsetY) < 0)
                || ((position.y + offsetY) >= 15));
        newPosition = new Point(position.x + offsetX, position.y + offsetY);
        matrix.moveAnimal(this,newPosition);
    }

    public Animal tryRespoduction() {
        timeAfterReproduction = 0;
        /*
        todo searchPositionForChild - это уже лучше, у мира спрашиваем где можно бахнуть потомка
         */
        Point positionForChild = matrix.searchPositionForChild(this);
        Animal newAnimal = null;
        boolean result;
        if (positionForChild != null) {
            /*
            todo есть же factory
             */
            if (this instanceof Penguin) {
                newAnimal = new Penguin(matrix);
            } else {
                newAnimal = new Orca(matrix);
            }
            /*
            todo разрыв в логике: установка позиции а animal отделена от втыкания в матрицу
             */
            newAnimal.setPosition(positionForChild.x, positionForChild.y);
            matrix.putAnimal(newAnimal,positionForChild);
            result = true;
        } else {
            result = false;
        }
        return newAnimal;
    }

    public void step() {
        timeAfterReproduction++;
        move();
        if (timeAfterReproduction > timeBetweenReproduction) {
            tryRespoduction();
        }
    }

}
