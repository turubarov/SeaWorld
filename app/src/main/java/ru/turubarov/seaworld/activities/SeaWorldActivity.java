package ru.turubarov.seaworld.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import ru.turubarov.seaworld.R;
import ru.turubarov.seaworld.adapters.SeaWorldAdapter;
import ru.turubarov.seaworld.data.SeaWorld;
import ru.turubarov.seaworld.data.Settings;

public class SeaWorldActivity extends AppCompatActivity {
    private GridView seaWorldGrid;
    private Button restartButton;

    private SeaWorld seaWorld;

    private int numOfColumns;
    private int numOfRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sea_world);

        Settings.getInstance().init(this);

        seaWorld = new SeaWorld();

        numOfColumns = Settings.getInstance().getNumOfColumns();
        numOfRows = Settings.getInstance().getNumOfRows();

        seaWorldGrid = (GridView) findViewById(R.id.seaWorldGrid);
        seaWorldGrid.setNumColumns(numOfColumns);

        final SeaWorldAdapter adapter = new SeaWorldAdapter(this, seaWorldGrid, seaWorld.getAnimalMatrix());
        seaWorldGrid.setAdapter(adapter);
        seaWorldGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                seaWorld.step();
                adapter.notifyDataSetChanged();
            }
        });

        restartButton = (Button) findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seaWorld.fullSeaWorld();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
