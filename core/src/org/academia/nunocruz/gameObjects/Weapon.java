package org.academia.nunocruz.gameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.screens.PlayScreen;

/**
 *  Ana
 */
public class Weapon extends Sprite {

    public int damage = 3;
    public Body b2body;
    public World world;
    public PlayScreen screen;

    public Weapon(PlayScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        defineWeapon();
        //defineJoint();
    }
/*
    public void defineJoint() {

        WeldJointDef jointDef = new WeldJointDef();

        jointDef.bodyA = screen.luke.b2body;
        jointDef.bodyB = this.b2body;

        jointDef.localAnchorA.add(0.15f, 0);

        world.createJoint(jointDef);
    }
*/


    public void defineWeapon() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(64 / StarWars.PPM, 64 / StarWars.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = StarWars.LIGHTSABER_BIT;
        fixtureDef.filter.maskBits = StarWars.ENEMY_BIT;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8 / StarWars.PPM, 16 / StarWars.PPM);

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);
    }


}
