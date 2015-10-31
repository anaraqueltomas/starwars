package org.academia.nunocruz.gameObjects.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.screens.PlayScreen;

public class Brick extends TileObject {

    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);

        fixture.setUserData(this);
        setCategoryFilter(StarWars.BRICK_BIT);
    }

    @Override
    public void onHit() {

    }
}
