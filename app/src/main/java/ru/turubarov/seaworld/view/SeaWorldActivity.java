package ru.turubarov.seaworld.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import ru.turubarov.seaworld.R;
import ru.turubarov.seaworld.adapters.SeaWorldAdapter;
import ru.turubarov.seaworld.model.SeaWorldModel;
import ru.turubarov.seaworld.model.animals.Animal;
import ru.turubarov.seaworld.presenter.ISeaWorldPresenter;
import ru.turubarov.seaworld.presenter.SeaWorldPresenter;
import ru.turubarov.seaworld.settings.SettingsOfSeaWorld;

public class SeaWorldActivity extends AppCompatActivity implements ISeaWorldView,
        AdapterView.OnItemClickListener,
        View.OnClickListener {

    private GridView seaWorldGrid;
    private Button restartButton;

    private int numOfColumns;
    private int numOfRows;

    private SeaWorldAdapter adapter;

    private ISeaWorldPresenter seaWorldPresenter;

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sea_world);

        mContext = this;
        SettingsOfSeaWorld.getInstance().init();

        numOfColumns = SettingsOfSeaWorld.getInstance().getNumOfColumns();
        numOfRows = SettingsOfSeaWorld.getInstance().getNumOfRows();

        seaWorldGrid = (GridView) findViewById(R.id.seaWorldGrid);
        seaWorldGrid.setNumColumns(numOfColumns);

        seaWorldGrid.setAdapter(adapter);
        seaWorldGrid.setOnItemClickListener(this);

        restartButton = (Button) findViewById(R.id.restartButton);
        restartButton.setOnClickListener(this);

        seaWorldPresenter = new SeaWorldPresenter(new SeaWorldModel(numOfColumns, numOfRows), this);
    }

    @Override
    public void setSeaWorldData(Animal[][] animals) {
        adapter = new SeaWorldAdapter(this, seaWorldGrid, animals);
        seaWorldGrid.setAdapter(adapter);
    }

    @Override
    public void refreshData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.restartButton:
                seaWorldPresenter.onRestartListener();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        seaWorldPresenter.onStepListener();
    }

    public static Context getContext(){
        return mContext;
    }
}
