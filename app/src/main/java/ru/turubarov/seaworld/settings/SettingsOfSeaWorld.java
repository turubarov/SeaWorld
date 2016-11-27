package ru.turubarov.seaworld.settings;

import android.content.Context;

import ru.turubarov.seaworld.R;

/**
 * Created by Александр on 26.11.2016.
 */
public class SettingsOfSeaWorld {
    private static SettingsOfSeaWorld ourInstance = new SettingsOfSeaWorld();

    public static SettingsOfSeaWorld getInstance() {
        return ourInstance;
    }

    private int numOfColumns;
    private int numOfRows;
    private int percentOfPenguin;
    private int percentOfOrca;
    private int reproductionRateOfOrca;
    private int reproductionRateOfPenguin;
    private int liveWithoutEat;

    private Context context;

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

    public void init(Context context) {
        this.context = context;

        numOfColumns = context.getResources().getInteger(R.integer.num_of_columns);
        numOfRows = context.getResources().getInteger(R.integer.num_of_rows);
        percentOfOrca = context.getResources().getInteger(R.integer.percent_of_orca);
        percentOfPenguin = context.getResources().getInteger(R.integer.percent_of_penguin);
        reproductionRateOfOrca = context.getResources().getInteger(R.integer.reproduction_rate_of_orca);
        reproductionRateOfPenguin = context.getResources().getInteger(R.integer.reproduction_rate_of_penguin);
        liveWithoutEat = context.getResources().getInteger(R.integer.live_without_eat);
    }

    private SettingsOfSeaWorld() {

    }
}
