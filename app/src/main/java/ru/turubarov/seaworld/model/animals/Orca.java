package ru.turubarov.seaworld.model.animals;

import java.util.ArrayList;

import ru.turubarov.seaworld.R;
import ru.turubarov.seaworld.enums.AnimalTypes;
import ru.turubarov.seaworld.model.SeaWorldModel;
import ru.turubarov.seaworld.settings.SettingsOfSeaWorld;

/**
 * Created by Александр on 22.11.2016.
 */

public class Orca extends Animal {

    /*
    todo вопрос: модификаторы доступа
    исправил
     */
    private int timeAfterEat;

    public Orca(SeaWorldModel matrix) {
        super(matrix);
        timeBetweenReproduction = SettingsOfSeaWorld.getInstance().getReproductionRateOfOrca();
        timeAfterEat = 0;
    }

    @Override
    public int getDrawableResourceId() {
        return R.drawable.orca;
    }

    @Override
    public void step() {
        timeAfterReproduction++;
        if (!eat()) {
            move();
            timeAfterEat++;
        } else {
            timeAfterEat = 0;
        }
        if (timeAfterEat > SettingsOfSeaWorld.getInstance().getLiveWithoutEat()) {
            matrix.removeAnimal(this);
        } else {
            if (timeAfterReproduction > timeBetweenReproduction) {
                tryRespoduction();
            }
        }
    }

    public boolean eat() {
        boolean result = true;
        int curX = position.x;
        int curY = position.y;
        Penguin tuxForEat = searchFood();
        // перемещаем касатку на место съеденного пингвина
        if (tuxForEat != null) {
            matrix.moveAnimal(this, tuxForEat.position, false);
            tuxForEat.setPosition(curX, curY);
            tuxForEat.isDead = true;
            result = true;
        } else { // если кушать нечего, то ничего не делаем
            result = false;
        }
        return result;
    }

    public Penguin searchFood() {
        int curX = position.x;
        int curY = position.y;
        int radius = SettingsOfSeaWorld.getInstance().getVisibleRadius();
        ArrayList<Penguin> foods = new ArrayList<Penguin>();
        // в цикле ищем пингвина на съедение
        for (int j = 0; j < 2 * radius + 1; j++) {
            for (int k = 0; k < 2 * radius + 1; k++) {
                if ((j != radius || k != radius) &&
                        matrix.hitPointOnRange(curX + j - radius, curY + k - radius)) {
                    if (matrix.getAnimal(curX + j - radius, curY + k - radius) instanceof Penguin) {
                        foods.add((Penguin)matrix.getAnimal(curX + j - radius, curY + k - radius));
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

    @Override
    public Animal getChild() {
        try {
            return matrix.getAnimalFactory().createAnimal(AnimalTypes.ORCA);
        }
        catch (Exception e) {
            return null;
        }
    }
}
