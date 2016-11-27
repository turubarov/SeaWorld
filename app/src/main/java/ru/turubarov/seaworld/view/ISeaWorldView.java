package ru.turubarov.seaworld.view;

import ru.turubarov.seaworld.model.animals.Animal;

/**
 * Created by Александр on 27.11.2016.
 */

public interface ISeaWorldView {
    public void refreshData();
    public void setSeaWorldData(Animal[][] animals);
}
