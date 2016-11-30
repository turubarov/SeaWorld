package ru.turubarov.seaworld.presenter;

import ru.turubarov.seaworld.model.ISeaWorldModel;
import ru.turubarov.seaworld.view.ISeaWorldView;

/**
 * Created by Александр on 27.11.2016.
 */

public class SeaWorldPresenter implements ISeaWorldPresenter {

    /*
    todo общий вопрос про презентеры
    Модель MVP нужна для отделения графического представления (Activity, Fragment)
    от объектов данных.
    Presenter - посредник между графическим представлением и моделью.
    Он получает данные из модели и передаёт их представлению в удобной форме.
     */
    private ISeaWorldModel seaWorldModel;
    private ISeaWorldView seaWorldView;

    public SeaWorldPresenter(ISeaWorldModel seaWorld, ISeaWorldView seaWorldView ) {
        this.seaWorldModel = seaWorld;
        this.seaWorldView = seaWorldView;
        this.seaWorldView.setSeaWorldData(seaWorld.getAnimalMatrix());
    }


    @Override
    public void onRestartListener() {
        seaWorldModel.fullSeaWorld();
        seaWorldView.refreshData();
    }

    @Override
    public void onStepListener() {
        seaWorldModel.stepOfSeaWorld();
        seaWorldView.refreshData();
    }
}
