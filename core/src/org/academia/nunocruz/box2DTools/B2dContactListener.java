package org.academia.nunocruz.box2DTools;

import com.badlogic.gdx.physics.box2d.*;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.gameObjects.Enemies.Enemy;
import org.academia.nunocruz.gameObjects.Luke;
import org.academia.nunocruz.gameObjects.TileObjects.EnergyGlobe;
import org.academia.nunocruz.gameObjects.TileObjects.SpaceShip;
import org.academia.nunocruz.gameObjects.TileObjects.Yoda;
import org.academia.nunocruz.gameObjects.Weapon;

public class B2dContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

            case StarWars.LUKE_BIT | StarWars.ENEMY_BIT:

                //Se a fixA for o Luke chama o metodo hit, se não chama o metodo na fixtureB;
                if (fixA.getFilterData().categoryBits == StarWars.LUKE_BIT) {
                    ((Luke) fixA.getUserData()).hit(((Enemy) fixB.getUserData()).damage);
                } else {
                    ((Luke) fixB.getUserData()).hit(((Enemy) fixA.getUserData()).damage);
                }
                break;

            case StarWars.LIGHTSABER_BIT | StarWars.ENEMY_BIT:

                if (fixA.getFilterData().categoryBits == StarWars.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).hit(((Weapon) fixB.getUserData()).damage);

                } else {
                    ((Enemy) fixB.getUserData()).hit(((Weapon) fixA.getUserData()).damage);
                }
                break;

            case StarWars.LUKE_BIT | StarWars.ENERGYGLOBE_BIT:

                if (fixA.getFilterData().categoryBits == StarWars.LUKE_BIT) {
                    ((Luke) fixA.getUserData()).gainHealth(((EnergyGlobe) fixB.getUserData()).health);
                    ((Luke) fixA.getUserData()).gainScore(((EnergyGlobe) fixB.getUserData()).score);
                    ((EnergyGlobe) fixB.getUserData()).onHit();

                } else {
                    ((Luke) fixB.getUserData()).gainHealth(((EnergyGlobe) fixA.getUserData()).health);
                    ((Luke) fixB.getUserData()).gainScore(((EnergyGlobe) fixA.getUserData()).score);
                    ((EnergyGlobe) fixA.getUserData()).onHit();
                }
                break;

            case StarWars.LUKE_BIT | StarWars.SPACESHIP_BIT:
                if (fixA.getFilterData().categoryBits == StarWars.LUKE_BIT) {
                    ((SpaceShip) fixB.getUserData()).onHit();

                } else {
                    ((SpaceShip) fixA.getUserData()).onHit();
                }
                break;
            case StarWars.LUKE_BIT | StarWars.YODA_BIT:
                if (fixA.getFilterData().categoryBits == StarWars.LUKE_BIT) {
                    ((Yoda) fixB.getUserData()).onHit();

                } else {
                    ((Yoda) fixA.getUserData()).onHit();
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
