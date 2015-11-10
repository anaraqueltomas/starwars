package org.academia.nunocruz.gameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.screens.PlayScreen;

/**
 * Created by brentaureli on 10/12/15.
 */
public class Bullet extends Sprite {

    PlayScreen screen;
    World world;
    Array<TextureRegion> frames;
    Animation bulletAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;

    Body b2body;

    public Bullet(PlayScreen screen, float x, float y, boolean fireRight) {
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        frames = new Array<TextureRegion>();

        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("energy"), i * 32, 0, 16, 16));
        }
        bulletAnimation = new Animation(0.2f, frames);
        setRegion(bulletAnimation.getKeyFrame(0));
        setBounds(x, y, 6 / StarWars.PPM, 6 / StarWars.PPM);
        defineBullet();
    }

    public void defineBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 12 / StarWars.PPM : getX() - 12 /StarWars.PPM, getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        if (!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / StarWars.PPM);
        fdef.filter.categoryBits = StarWars.BULLET_BIT;
        fdef.filter.maskBits = StarWars.GROUND_BIT | StarWars.ENERGYGLOBE_BIT |
                StarWars.BRICK_BIT | StarWars.ENEMY_BIT | StarWars.OBJECT_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2(fireRight ? 2 : -2, 0));
    }

    public void update(float dt) {
        stateTime += dt;
        setRegion(bulletAnimation.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if ((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        if ((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
            setToDestroy();
    }

    public void setToDestroy() {
        setToDestroy = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }


}
