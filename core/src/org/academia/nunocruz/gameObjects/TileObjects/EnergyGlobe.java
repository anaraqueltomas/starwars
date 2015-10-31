package org.academia.nunocruz.gameObjects.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.screens.PlayScreen;

public class EnergyGlobe extends TileObject {

    public int score = 10;
    public int health = 2;

    private Sound energySound;

    public EnergyGlobe(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(StarWars.ENERGYGLOBE_BIT);

    }

    @Override
    public void onHit() {

        //energySound = StarWars.manager.get("audio/sounds/catchEnergy.wav");
        //energySound.play();
        setCategoryFilter(StarWars.DESTROYED_BIT);
        getCell().setTile(null);

    }
}
