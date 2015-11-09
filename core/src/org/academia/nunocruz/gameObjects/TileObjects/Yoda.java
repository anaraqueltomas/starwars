package org.academia.nunocruz.gameObjects.TileObjects;

import com.badlogic.gdx.math.Rectangle;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.gameObjects.Luke;
import org.academia.nunocruz.screens.PlayScreen;


public class Yoda extends TileObject {

    private Luke luke;

    public Yoda(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(StarWars.YODA_BIT);
        this.luke = screen.luke;
    }

    @Override
    public void onHit() {
        this.luke.becomeJedi();
    }
}
