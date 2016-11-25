package ru.turubarov.seaworld.animals;

import android.graphics.Point;
import java.util.Random;

/**
 * Created by Александр on 22.11.2016.
 */

public class Animal {
    public Point position;
    public int age;
    public int lastEat;
    public int lastChild;
    int currentDirection;
    Animal[][] seaWorldField;
    Random rand;
    public boolean isDead;
    public boolean isNewBorn;

    public Animal(Animal[][] seaWorldField) {
        this.seaWorldField = seaWorldField;
        position = new Point();
        //position.set(x, y);
        age = lastEat = lastChild = 0;
        rand = new Random();
        isDead = false;
        isNewBorn = true;
    }

    public void setPosition(int x, int y) {
        position.set(x, y);
    }

    public void step() {
        age++;
        lastEat++;
        lastChild++;
        isNewBorn = false;
    }

    public void eat() {
        age++;
        lastEat = 0;
    }

    public void child() {
        age++;
        lastChild = 0;
    }

    public int getLastEat() {
        return this.lastEat;
    }

    public int getLastChild() {
        return  this.lastChild;
    }

    public Point calcNextPosition() {
        int offsetX, offsetY;
        do {
            offsetX = rand.nextInt(3) - 1;
            offsetY = rand.nextInt(3) - 1;
        } while ((offsetX == 0 && offsetY == 0)
                || ((position.x + offsetX) < 0)
                || ((position.x + offsetX) >= 15)
                || ((position.y + offsetY) < 0)
                || ((position.y + offsetY) >= 10));
        return new Point(position.x + offsetX, position.y + offsetY);
    }

}
