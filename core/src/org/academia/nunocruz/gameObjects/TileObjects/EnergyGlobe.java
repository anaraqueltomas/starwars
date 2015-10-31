package org.academia.nunocruz.gameObjects.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.screens.PlayScreen;


public class EnergyGlobe extends TileObject {

    private static TiledMapTileSet tileSet;
    public int score = 10;
    public int health = 5;
    private int BLANK_ENERGY = 28;

    public EnergyGlobe(PlayScreen screen, MapObject object){
        super(screen, object);

        tileSet = map.getTileSets().getTileSet("tileExemplo");
        fixture.setUserData(this);
        setCategoryFilter(StarWars.ENERGYGLOBE_BIT);
    }

    @Override
    public void onHit() {


    }
}
