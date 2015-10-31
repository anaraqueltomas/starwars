package org.academia.nunocruz.gameObjects.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.gameObjects.Luke;
import org.academia.nunocruz.screens.PlayScreen;

public class SpaceShip extends TileObject {

    public SpaceShip(PlayScreen screen, Rectangle bounds) {

        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(StarWars.SPACESHIP_BIT);
    }

    @Override
    public void onHit() {
        Luke.gainScore(50);
        Sound victorySound = StarWars.manager.get("audio/sounds/victorySound.mp3");
        victorySound.play();
        PlayScreen.victory=true;

    }
}
