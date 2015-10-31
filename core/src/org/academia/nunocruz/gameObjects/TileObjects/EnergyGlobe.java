package org.academia.nunocruz.gameObjects.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.utils.Array;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.gameObjects.Luke;
import org.academia.nunocruz.screens.PlayScreen;

public class EnergyGlobe extends TileObject {

    private static TiledMapTileSet tileSet;
    public int score = 10;
    public int health = 5;
    private int BLANK_ENERGY = 28;

    private Sound energySound;
    private Array<TextureRegion> energyGlobes;
    private Animation energyAnimation;

    boolean setToDestroy;
    boolean destroyed;

    public EnergyGlobe(PlayScreen screen, MapObject object) {
        super(screen, object);

        tileSet = map.getTileSets().getTileSet("energy");
        fixture.setUserData(this);
        setCategoryFilter(StarWars.ENERGYGLOBE_BIT);


        energyGlobes = new Array<TextureRegion>();

        for (int i = 0; i < 6; i++) {
            energyGlobes.add(new TextureRegion(screen.getAtlas().findRegion("energy"), i * 32, 0, 32, 32));
        }

        energyAnimation = new Animation(0.4f, energyGlobes);
        setBounds(getX(), getY(), 32 / StarWars.PPM, 32 / StarWars.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    @Override
    public void onHit() {

        energySound = StarWars.manager.get("audio/sounds/catchEnergy.wav");
        energySound.play();

        setToDestroy = true;
    }
}
