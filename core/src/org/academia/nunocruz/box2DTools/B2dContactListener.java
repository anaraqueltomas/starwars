package org.academia.nunocruz.box2DTools;

import com.badlogic.gdx.physics.box2d.*;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.gameObjects.Bullet;
import org.academia.nunocruz.gameObjects.Enemies.Enemy;
import org.academia.nunocruz.gameObjects.Luke;
import org.academia.nunocruz.gameObjects.TileObjects.EnergyGlobe;
import org.academia.nunocruz.gameObjects.TileObjects.SpaceShip;
import org.academia.nunocruz.gameObjects.TileObjects.Yoda;


public class B2dContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        Object objLuke;
        Object objOther;

        if (fixA.getUserData() instanceof Luke) {
            objLuke = fixA.getUserData();
            objOther = fixB.getUserData();
        } else {
            objLuke = fixB.getUserData();
            objOther = fixA.getUserData();
        }

        switch (fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits) {


            case StarWars.LUKE_BIT | StarWars.ENEMY_BIT:

                // if Luke contacts an Enemy, it will beHitBy that Enemy and suffer the damage

                ((Luke)objLuke).beHitBy(((Enemy)objOther).getDamage());
                break;


            case StarWars.LUKE_BIT | StarWars.ENERGYGLOBE_BIT:

                // if Luke contacts a EnergyGlobe, it will gainHealth and Score
                // and the EnergyGlobe will disappear

                ((Luke)objLuke).gainHealth( ((EnergyGlobe)objOther).getHealth());
                ((Luke)objLuke).gainScore( ((EnergyGlobe)objOther).getScore());
                ((EnergyGlobe)objOther).onHit();
                break;


            case StarWars.LUKE_BIT | StarWars.SPACESHIP_BIT:

                ((SpaceShip)objOther).onHit();
                break;


            case StarWars.LUKE_BIT | StarWars.YODA_BIT:

                ((Yoda)objOther).onHit();
                break;

            case StarWars.BULLET_BIT | StarWars.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == StarWars.BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).setToDestroy();
                    ((Enemy) fixB.getUserData()).setToDestroy();

                }
                else{
                    ((Bullet) fixB.getUserData()).setToDestroy();
                    ((Enemy) fixA.getUserData()).setToDestroy();
            }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
