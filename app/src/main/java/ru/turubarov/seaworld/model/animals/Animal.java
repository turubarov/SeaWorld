package ru.turubarov.seaworld.model.animals;

import android.graphics.Point;
import java.util.Random;

import ru.turubarov.seaworld.model.SeaWorldModel;
import ru.turubarov.seaworld.settings.SettingsOfSeaWorld;

/**
 * Created by Александр on 22.11.2016.
 */

public class Animal {

    public Point position;
    protected int timeAfterReproduction;
    protected int timeBetweenReproduction;

    SeaWorldModel matrix;
    /*
    todo у каждого Animal свой личный рандом... а нужно ли?
    вообще-то, не нужно. но тогда мне придётся передавать его в конструктор или в сеттер
    или, например, присваивать из синглтона
     */
    Random rand;

    public boolean isDead;

    public Animal(SeaWorldModel matrix) {
        this.matrix = matrix;
        position = new Point();
         timeAfterReproduction = 0;
        rand = new Random();
        isDead = false;

        /*
        todo чтоб такого кода не было, можно воспользовать наследованием. соответственно, вопрос: как?
        перенести инициализацию timeBetweenReproduction в классы-наследники
         */
        /*if (this instanceof Penguin) {
            timeBetweenReproduction = SettingsOfSeaWorld.getInstance().getReproductionRateOfPenguin();
        } else if (this instanceof Orca) {
            timeBetweenReproduction = SettingsOfSeaWorld.getInstance().getReproductionRateOfOrca();
        }*/
    }

    public void setPosition(int x, int y) {
        position.set(x, y);
    }

    public void move() {
        int offsetX, offsetY;
        Point newPosition;
        int visibleRadius = SettingsOfSeaWorld.getInstance().getVisibleRadius();
        /*
        todo "magic numbers"... много "magic numbers"
        избавился. отправил "magic numbers" в ресурсы
         */
        do {
            offsetX = rand.nextInt(2 * visibleRadius + 1) - 1;
            offsetY = rand.nextInt(2 * visibleRadius + 1) - 1;
            /*
            todo походит на range check, по идее эта логика должна быть в классе,
                 реализующем среду, в которой водятся животные, а не в самом животном

                 в первой строчке я исключаю случай, когда животное перемещается в то же место
                 где оно было. остальное - действительно range check
             */
        } while ((offsetX == 0 && offsetY == 0)
                || !matrix.hitPointOnRange(position.x + offsetX, position.y + offsetY));
        newPosition = new Point(position.x + offsetX, position.y + offsetY);
        matrix.moveAnimal(this, newPosition, true);
    }

    public Animal getChild() {
        return null;
    }

    public Animal tryRespoduction() {
        timeAfterReproduction = 0;
        /*
        todo searchPositionForChild - это уже лучше, у мира спрашиваем где можно бахнуть потомка
         */
        Point positionForChild = matrix.searchPositionForChild(this);
        Animal newAnimal = null;
        if (positionForChild != null) {
            /*
            todo есть же factory
            да, есть. переделал. кстати, заодно можно и от instanceof избавиться
             */
            /*if (this instanceof Penguin) {
                newAnimal = new Penguin(matrix);
            } else {
                newAnimal = new Orca(matrix);
            }*/
            newAnimal = getChild();
            /*
            todo разрыв в логике: установка позиции а animal отделена от втыкания в матрицу
            исправил
             */
            //newAnimal.setPosition(positionForChild.x, positionForChild.y);
            matrix.putAnimal(newAnimal, positionForChild);
        } /*else {
        }*/
        return newAnimal;
    }



    public void step() {
        timeAfterReproduction++;
        move();
        if (timeAfterReproduction > timeBetweenReproduction) {
            tryRespoduction();
        }
    }

    public int getDrawableResourceId() {
        return 0;
    }

}
