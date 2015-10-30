package org.academia.nunocruz.gameObjects.Enemies;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.screens.PlayScreen;

/**
 * Created by brentaureli on 9/14/15.
 */
public abstract class Enemy extends Sprite {

    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    boolean setToDestroy;
    boolean destroyed;

    public int health;
    public int damage;

    public Enemy(PlayScreen screen, float x, float y){

        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(-1, -2);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();

    public abstract void update(float dt);

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }


    public void hit(int n) {

        health -= n;
        if (health <= 0) { setToDestroy = true; }

    }
}

