package org.academia.nunocruz.gameObjects.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.screens.PlayScreen;

/**
 * Ana
 */
public class Jawa extends Enemy {

    private float stateTime;
    private Animation walkAnimation;


    public Jawa(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        health = 6;
        damage = 1;
        score = 5;

        Array<TextureRegion> jawaWalk = new Array<TextureRegion>();

        for (int i = 0; i < 2; i++) {
            jawaWalk.add(new TextureRegion(screen.getAtlas().findRegion("jawa"), i * 32, 0, 32, 32));


            walkAnimation = new Animation(0.4f, jawaWalk);
            stateTime = 0;
            setBounds(getX(), getY(), 32 / StarWars.PPM, 32 / StarWars.PPM);
            setToDestroy = false;
            destroyed = false;
        }
    }

    public void update(float dt) {

        stateTime += dt;
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("jawa"), 32, 0, 32, 32));
            stateTime = 0;
        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8 / StarWars.PPM, 16 / StarWars.PPM);

        fdef.filter.categoryBits = StarWars.ENEMY_BIT;

        fdef.filter.maskBits = StarWars.GROUND_BIT | StarWars.ENERGYGLOBE_BIT |
                StarWars.ENEMY_BIT | StarWars.PLATFORM_BIT | StarWars.LUKE_BIT | StarWars.BULLET_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch) {

        if (!destroyed || stateTime < 1)
            super.draw(batch);
    }
}
