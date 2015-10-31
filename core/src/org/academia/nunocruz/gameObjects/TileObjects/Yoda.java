package org.academia.nunocruz.gameObjects.TileObjects;

import com.badlogic.gdx.math.Rectangle;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.screens.PlayScreen;

/**
 * Created by nunocruz on 31/10/15.
 */
public class Yoda extends TileObject {
    public Yoda(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(StarWars.YODA_BIT);
    }

    @Override
    public void onHit() {
        System.out.println("Tocou");
    }
}
