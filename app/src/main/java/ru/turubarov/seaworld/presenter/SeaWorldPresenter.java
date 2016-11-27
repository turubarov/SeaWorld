package ru.turubarov.seaworld.presenter;

import ru.turubarov.seaworld.model.ISeaWorld;
import ru.turubarov.seaworld.model.SeaWorld;
import ru.turubarov.seaworld.view.ISeaWorldView;

/**
 * Created by Александр on 27.11.2016.
 */

public class SeaWorldPresenter implements ISeaWorldPresenter {

    private ISeaWorld seaWorld;
    private ISeaWorldView seaWorldView;

    public SeaWorldPresenter(ISeaWorld seaWorld, ISeaWorldView seaWorldView ) {
        this.seaWorld = seaWorld;
        this.seaWorldView = seaWorldView;
        this.seaWorldView.setSeaWorldData(seaWorld.getAnimalMatrix());
    }


    @Override
    public void onRestartListener() {
        seaWorld.fullSeaWorld();
        seaWorldView.refreshData();
    }

    @Override
    public void onStepListener() {
        seaWorld.stepOfSeaWorld();
        seaWorldView.refreshData();
    }
}
