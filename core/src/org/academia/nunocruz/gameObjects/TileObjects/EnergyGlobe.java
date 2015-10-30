package org.academia.nunocruz.gameObjects.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.screens.PlayScreen;

/**
 * Created by nunocruz on 29/10/15.
 */
public class EnergyGlobe extends TileObject {

    private static TiledMapTileSet tileSet;

    public EnergyGlobe(PlayScreen screen, MapObject object){
        super(screen, object);

        tileSet = map.getTileSets().getTileSet("tileExemplo");
        fixture.setUserData(this);
        setCategoryFilter(StarWars.ENERGYGLOBE_BIT);
    }
}