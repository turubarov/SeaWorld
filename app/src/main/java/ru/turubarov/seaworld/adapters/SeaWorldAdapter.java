package ru.turubarov.seaworld.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Александр on 22.11.2016.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import ru.turubarov.seaworld.R;
import ru.turubarov.seaworld.animals.Animal;
import ru.turubarov.seaworld.animals.Orca;
import ru.turubarov.seaworld.animals.Tux;

public class SeaWorldAdapter extends BaseAdapter {
    private Context mContext;
    private GridView myGv;
    private Animal[][] organisms;

    public SeaWorldAdapter(Context c, GridView gv, Animal[][] org) {
        int test = c.getResources().getInteger(R.integer.num_of_collumns);
        mContext = c;
        this.myGv = gv;
        this.organisms = org;
    }

    public int getCount() {
        return 15*10;
    }

    public Object getItem(int position) {
        return organisms[position%10][(int)(position/10)];
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        if (organisms[position%15][(int)(position/15)] instanceof Orca)

            imageView.setImageResource(R.drawable.orca);
        else if (organisms[position%15][(int)(position/15)] instanceof Tux)
            imageView.setImageResource(R.drawable.tux);
        else
            imageView.setImageResource(0);

        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                android.view.ViewGroup.LayoutParams.FILL_PARENT,
                myGv.getHeight()/10);
        imageView.setLayoutParams(param);

        return imageView;
    }

}
