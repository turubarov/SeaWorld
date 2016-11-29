package ru.turubarov.seaworld.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;

/**
 * Created by Александр on 22.11.2016.
 */

import android.widget.ImageView;

import ru.turubarov.seaworld.R;
import ru.turubarov.seaworld.model.animals.Animal;
import ru.turubarov.seaworld.model.animals.Orca;
import ru.turubarov.seaworld.model.animals.Penguin;
import ru.turubarov.seaworld.settings.SettingsOfSeaWorld;

/**
 * Адаптер для отображения игрового мира в таблице на экране
 */
public class SeaWorldAdapter extends BaseAdapter {
    private Context context;
    private GridView gridView;
    private Animal[][] animals;

    private int numOfColumns;
    private int numOfRows;

    public SeaWorldAdapter(Context context, GridView gridView, Animal[][] animals) {
        this.context = context;
        this.gridView = gridView;
        this.animals = animals;

        /*
        TODO зачем у настроек просить размеры? массив animals разве не дает эту информацию?
         */
        this.numOfColumns = SettingsOfSeaWorld.getInstance().getNumOfColumns();
        this.numOfRows = SettingsOfSeaWorld.getInstance().getNumOfRows();
    }

    public int getCount() {
        return numOfColumns * numOfRows;
    }

    public Object getItem(int position) {
        return animals[position % numOfColumns][(int)(position / numOfColumns)];
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        /*
        todo 1. вроде ж специально метод getItem сделал для такой лыжы "animals[position % numOfColumns][(int)(position / numOfColumns)]"...
        todo 2. почему instanceof? есть альтернативы?
        todo 3. от этого "свича" вообще можно избавиться. есть мысли как?
         */
        if (animals[position % numOfColumns][(int)(position / numOfColumns)] instanceof Orca)

            imageView.setImageResource(R.drawable.orca);
        else if (animals[position % numOfColumns][(int)(position / numOfColumns)] instanceof Penguin)
            imageView.setImageResource(R.drawable.tux);
        else
            imageView.setImageResource(0);

        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT,
                gridView.getHeight() / numOfRows);
        imageView.setLayoutParams(param);

        return imageView;
    }

}
