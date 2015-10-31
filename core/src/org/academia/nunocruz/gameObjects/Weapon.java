package org.academia.nunocruz.gameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import org.academia.nunocruz.StarWars;

/**
 *  Ana
 */
public class Weapon extends Sprite {

    public int damage = 3;



    public void defineLightsaber(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(64/ StarWars.PPM,64/ StarWars.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = StarWars.LIGHTSABER_BIT;
        fixtureDef.filter.maskBits = StarWars.ENEMY_BIT;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8/StarWars.PPM,16/StarWars.PPM);

        fixtureDef.shape = shape;

        b2body.createFixture(fixtureDef).setUserData(this);
    }


}
