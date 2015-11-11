package org.academia.nunocruz.gameObjects.TileObjects;

import com.badlogic.gdx.math.Rectangle;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.screens.PlayScreen;

public class EnergyGlobe extends TileObject {

    private int score;
    private int health;

    public EnergyGlobe(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        score = 10;
        health = 2;

        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(StarWars.ENERGYGLOBE_BIT);

    }

    public int getScore() {
        return score;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void onHit() {
        setCategoryFilter(StarWars.DESTROYED_BIT);
        //getCell().setTile(null);

    }
}
