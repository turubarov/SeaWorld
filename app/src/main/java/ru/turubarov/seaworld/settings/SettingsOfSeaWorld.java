package ru.turubarov.seaworld.settings;

import android.content.Context;
import android.content.res.Resources;

import ru.turubarov.seaworld.R;
import ru.turubarov.seaworld.view.SeaWorldActivity;

/**
 * Created by Александр on 26.11.2016.
 */
public class SettingsOfSeaWorld {
    private static volatile SettingsOfSeaWorld ourInstance = new SettingsOfSeaWorld();


    /*
    todo общий вопрос по синглтонам: зачем нужны? когда можно использовать, когда нельзя? потокобезопасность
    Синглтон - это класс, который гарантирует создание только одного экземпляра объекта,
    и предоставляет глобальную точку доступа. Используется для сущностей,
    которые должны быть на всё приложение в единственном экземпляре (например, настройки)

    В данной реализации класс не является потокобезопасным. Несколько потоков могут войти в конструктор,
    и создать несколько экземпляров. Чтобы этого избежать, нужно добавить слово synchronized
    к объявлению getInstance().
    К объявлению переменной ourInstance нужно добавить ключевое слово volatile, чтобы избежать
    создания локальных копий данной переменной в каждом потоке.

     */
    public static synchronized SettingsOfSeaWorld getInstance() {
        return ourInstance;
    }

    private int numOfColumns;
    private int numOfRows;
    private int percentOfPenguin;
    private int percentOfOrca;
    private int reproductionRateOfOrca;
    private int reproductionRateOfPenguin;
    private int liveWithoutEat;
    private int visibleRadius;

    public int getNumOfColumns() {
        return numOfColumns;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getPercentOfPenguin() {
        return percentOfPenguin;
    }

    public int getPercentOfOrca() {
        return percentOfOrca;
    }

    public int getReproductionRateOfOrca() {
        return reproductionRateOfOrca;
    }

    public int getReproductionRateOfPenguin(){
        return reproductionRateOfPenguin;
    }

    public int getLiveWithoutEat() {
        return liveWithoutEat;
    }

    public int getVisibleRadius() {return  visibleRadius;}

    public void init() {
        /*
        todo а зачем контекст?
         */
        /*
        todo а-а, понятно зачем ). вопросы

            1. зачем сохранять в проперти контекст?
            можно и не сохранять. тогда нужно у активности создать статический метод
            public static Context getContext(){
                return mContext;
            }

            2. как брать ресурсы, если активити не доступна?
            Resources.getSystem(). Таким образом можно получить только системные ресурсы.

         */
        numOfColumns = SeaWorldActivity.getContext().getResources().getInteger(R.integer.num_of_columns);
        numOfRows = SeaWorldActivity.getContext().getResources().getInteger(R.integer.num_of_rows);
        percentOfOrca = SeaWorldActivity.getContext().getResources().getInteger(R.integer.percent_of_orca);
        percentOfPenguin = SeaWorldActivity.getContext().getResources().getInteger(R.integer.percent_of_penguin);
        reproductionRateOfOrca = SeaWorldActivity.getContext().getResources().getInteger(R.integer.reproduction_rate_of_orca);
        reproductionRateOfPenguin = SeaWorldActivity.getContext().getResources().getInteger(R.integer.reproduction_rate_of_penguin);
        liveWithoutEat = SeaWorldActivity.getContext().getResources().getInteger(R.integer.live_without_eat);
        visibleRadius = SeaWorldActivity.getContext().getResources().getInteger(R.integer.visible_raduis);
    }

    private SettingsOfSeaWorld() {

    }
}
