package org.academia.nunocruz.box2DTools;

import com.badlogic.gdx.physics.box2d.*;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.gameObjects.Enemies.Enemy;
import org.academia.nunocruz.gameObjects.Luke;

public class B2dContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){

            case StarWars.ENEMY_BIT | StarWars.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == StarWars.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case StarWars.LUKE_BIT | StarWars.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == StarWars.LUKE_BIT) {
                    ((Luke)fixA.getUserData()).hit();
                }
                else {
                    ((Luke)fixB.getUserData()).hit();
                }
                break;

            case StarWars.ENEMY_BIT | StarWars.ENEMY_BIT:

                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
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
