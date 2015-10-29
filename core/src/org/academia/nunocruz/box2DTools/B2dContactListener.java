package org.academia.nunocruz.box2DTools;

import com.badlogic.gdx.physics.box2d.*;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.gameObjects.Enemies.Enemy;
import org.academia.nunocruz.gameObjects.Luke;
import org.academia.nunocruz.gameObjects.TileObjects.TileObject;

/**
 * Created by nunocruz on 29/10/15.
 */
public class B2dContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case StarWars.MARIO_HEAD_BIT | StarWars.BRICK_BIT:
            case StarWars.MARIO_HEAD_BIT | StarWars.COIN_BIT:
                if(fixA.getFilterData().categoryBits == StarWars.MARIO_HEAD_BIT)
                    ((TileObject) fixB.getUserData()).onHeadHit((Luke) fixA.getUserData());
                else
                    ((TileObject) fixA.getUserData()).onHeadHit((Luke) fixB.getUserData());
                break;
            case StarWars.ENEMY_HEAD_BIT | StarWars.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == StarWars.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((Luke) fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((Luke) fixA.getUserData());
                break;
            case StarWars.ENEMY_BIT | StarWars.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == StarWars.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case StarWars.MARIO_BIT | StarWars.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == StarWars.MARIO_BIT) {
                            ((Luke) fixA.getUserData()).hit();
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
