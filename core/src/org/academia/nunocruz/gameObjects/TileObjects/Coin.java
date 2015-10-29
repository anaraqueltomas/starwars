package org.academia.nunocruz.gameObjects.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.gameObjects.Luke;
import org.academia.nunocruz.screens.PlayScreen;

/**
 * Created by nunocruz on 29/10/15.
 */
public class Coin extends TileObject {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Coin(PlayScreen screen, MapObject object){
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(StarWars.COIN_BIT);
    }

    @Override
    public void onHeadHit(Luke luke) {

    }
}
