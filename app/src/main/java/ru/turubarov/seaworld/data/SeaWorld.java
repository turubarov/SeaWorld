package ru.turubarov.seaworld.data;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

import ru.turubarov.seaworld.animals.Animal;
import ru.turubarov.seaworld.animals.Orca;
import ru.turubarov.seaworld.animals.Tux;

/**
 * Класс, отвечающий за работу с игровым миром
 * Created by Александр on 23.11.2016.
 */

public class SeaWorld {

    private static SeaWorld instance;

    private Animal[][] organisms;
    private ArrayList<Animal> orgamismsList;
    private Random rand;
    private Point p;

    private SeaWorld() {
        //fullSeaWorld();
        organisms = new Animal[15][10];
        orgamismsList = new ArrayList<Animal>();
        rand = new Random();
    }

    public Animal[][] getOrganisms() {
        return this.organisms;
    }

    public void fullSeaWorld() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 10; j++) {
                if (10 * i + j < 75)
                    organisms[i][j] = new Tux(organisms);
                else if (10 * i + j >= 75 && 10 * i + j < 83)
                    organisms[i][j] = new Orca(organisms);
                else
                    organisms[i][j] = null;
            }
        }

        int indexX, indexY;
        Random rand = new Random();
        Animal tmp;

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 10; j++) {
                indexX = rand.nextInt(15);
                indexY = rand.nextInt(10);
                tmp = organisms[i][j];
                organisms[i][j] = organisms[indexX][indexY];
                organisms[indexX][indexY] = tmp;
            }
        }
        orgamismsList.clear();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 10; j++) {
                if (organisms[i][j] != null) {
                    organisms[i][j].setPosition(i, j);
                    orgamismsList.add(organisms[i][j]);
                }
            }
        }
    }

    private Tux searchFood(Orca o) {
        int curX = o.position.x;
        int curY = o.position.y;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                if ((j != 1 || k != 1)
                        && curX + j - 1 >= 0 && curX + j - 1 < 15
                        && curY + k - 1 >= 0 && curY + k - 1 < 10) {
                    if (organisms[curX + j - 1][curY + k - 1] instanceof Tux) {
                        return (Tux)organisms[curX + j - 1][curY + k - 1];
                    }
                }
            }
        }
        return null;
    }

    private void move(Animal a, Point newPosition) {
        int curX = a.position.x;
        int curY = a.position.y;
        if (organisms[newPosition.x][newPosition.y] == null) {
            organisms[newPosition.x][newPosition.y] = a;
            organisms[curX][curY] = null;
            a.setPosition(newPosition.x, newPosition.y);
        }
    }

    private void eat(Orca o, Tux t) {
        int newX = t.position.x;
        int newY = t.position.y;
        organisms[newX][newY] = o;
        organisms[o.position.x][o.position.y] = null;
        o.setPosition(newX,newY);
        o.lastEat = 0;
        t.isDead = true;
    }

    private void dead(Orca o) {
        int x = o.position.x;
        int y = o.position.y;
        organisms[x][y] = null;
        o.isDead = true;
    }

    private void reproduction(Animal a) {
        int curX = a.position.x;
        int curY = a.position.y;

        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                if ((j != 1 || k != 1)
                        && curX + j - 1 >= 0 && curX + j - 1 < 15
                        && curY + k - 1 >= 0 && curY + k - 1 < 10) {
                    if (organisms[curX + j - 1][curY + k - 1] == null) {
                        a.lastChild = 0;
                        Animal newAnimal;
                        if (a instanceof Orca) {
                            newAnimal = new Orca(organisms);
                        } else {
                            newAnimal = new Tux(organisms);
                        }
                        newAnimal.setPosition(curX + j - 1, curY + k - 1);
                        organisms[curX + j - 1][curY + k - 1] = newAnimal;
                        orgamismsList.add(newAnimal);
                        return;
                    }
                }
            }
        }
    }

    public void step() {
        int newX,newY,direct;
        Animal organism;
        boolean flag;

        for (int i = 0; i < orgamismsList.size(); i++) {
            flag = false;
            organism = orgamismsList.get(i);
            if (organism instanceof Orca) { // касатка ищет, кого бы съесть
                Tux t = searchFood((Orca)organism);
                if (t != null) {
                    eat((Orca)organism,t);
                }
            }
        }

        for (int i = 0; i < orgamismsList.size(); i++) {
            organism = orgamismsList.get(i);
            if (organism.lastEat > 0 && organism.isDead == false) {
                Point next = organism.calcNextPosition(); // перемещение орагнизмов
                move(organism, next);
            }
        }

        for (int i = 0; i < orgamismsList.size(); i++) {
            organism = orgamismsList.get(i);
            if (organism instanceof Orca && organism.lastEat > 3) {
                dead((Orca)organism);
            }
        }

        for (int i = 0; i < orgamismsList.size(); ) {
            if (orgamismsList.get(i).isDead == true)
                orgamismsList.remove(i);
            else
                i++;
        }

        for (int i = 0; i < orgamismsList.size(); i++) {
            organism = orgamismsList.get(i);
            if ((organism.lastChild > 3 && organism instanceof Tux)
                    || (organism.lastChild > 12 && organism instanceof Orca))
                reproduction(organism);
        }

        for (int i = 0; i < orgamismsList.size(); i++) {
            orgamismsList.get(i).step();
        }
        int test = 3;
    }

    public static SeaWorld getInstance() {
        if (instance == null) {
            instance = new SeaWorld();
        }
        return instance;
    }


}
