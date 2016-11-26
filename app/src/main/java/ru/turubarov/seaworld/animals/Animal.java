package ru.turubarov.seaworld.animals;

import android.graphics.Point;
import java.util.Random;

import ru.turubarov.seaworld.data.AnimalMatrix;
import ru.turubarov.seaworld.data.Settings;

/**
 * Created by Александр on 22.11.2016.
 */

public class Animal {

    public Point position;
    protected int timeAfterReproduction;
    protected int timeBetweenReproduction;

    AnimalMatrix matrix;
    Random rand;

    public boolean isDead;

    public Animal(AnimalMatrix matrix) {
        this.matrix = matrix;
        position = new Point();
         timeAfterReproduction = 0;
        rand = new Random();
        isDead = false;

        if (this instanceof Penguin) {
            timeBetweenReproduction = Settings.getInstance().getReproductionRateOfPenguin();
        } else if (this instanceof Orca) {
            timeBetweenReproduction = Settings.getInstance().getReproductionRateOfOrca();
        }
    }

    public void setPosition(int x, int y) {
        position.set(x, y);
    }

    public void move() {
        int offsetX, offsetY;
        Point newPosition;
        do {
            offsetX = rand.nextInt(3) - 1;
            offsetY = rand.nextInt(3) - 1;
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
        Point positionForChild = matrix.searchPositionForChild(this);
        Animal newAnimal = null;
        boolean result;
        if (positionForChild != null) {
            if (this instanceof Penguin) {
                newAnimal = new Penguin(matrix);
            } else {
                newAnimal = new Orca(matrix);
            }
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
