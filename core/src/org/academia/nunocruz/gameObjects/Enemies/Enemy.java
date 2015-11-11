package org.academia.nunocruz.gameObjects.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import org.academia.nunocruz.gameObjects.Luke;
import org.academia.nunocruz.screens.PlayScreen;


public abstract class Enemy extends Sprite {

    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    boolean setToDestroy;
    boolean destroyed;

    protected int health;
    protected int damage;
    protected int score;


    public Enemy(PlayScreen screen, float x, float y) {

        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(-1, -2);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();

    public abstract void update(float dt);

    public void hit(int n) {
        health -= n;
        if (health <= 0) {
            setToDestroy = true;
            Luke.gainScore(score);
        }
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getScore() {
        return score;
    }
}

