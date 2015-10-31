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

public class Tusken extends Enemy {

    private float stateTime;
    private Animation walkAnimation;
<<<<<<< HEAD
    private Array<TextureRegion> tuskens;
=======
    private Array<TextureRegion> tuskenWalk;
    private boolean runningRight;
>>>>>>> 0f3d5b677bff9bca30d015f8b1e43416167dfbde


    /** Ana: Defining the hit method */

    public Tusken(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        health = 10;
        damage = 2;
        score = 10;

<<<<<<< HEAD
        tuskens = new Array<TextureRegion>();

        for(int i = 0; i < 3; i++) {
            tuskens.add(new TextureRegion(screen.getAtlas().findRegion("tusken-rideres"), i * 32, 0, 32, 32));
        }

        walkAnimation = new Animation(0.4f, tuskens);
=======
        tuskenWalk = new Array<TextureRegion>();

        for(int i = 21; i < 24; i++) {
            tuskenWalk.add(new TextureRegion(screen.getAtlas().findRegion("tusken-rideres"), i * 32, 0, 32, 32));
        }

        walkAnimation = new Animation(0.4f, tuskenWalk);
>>>>>>> 0f3d5b677bff9bca30d015f8b1e43416167dfbde
        setBounds(getX(), getY(), 32 / StarWars.PPM, 32 / StarWars.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt){

        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("tusken-rideres"), 32, 0, 32, 32));
            stateTime = 0;
        }
        else if(!destroyed) {
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
        shape.setAsBox(8 / StarWars.PPM, 16/StarWars.PPM);

        fdef.filter.categoryBits = StarWars.ENEMY_BIT;
        fdef.filter.maskBits = StarWars.GROUND_BIT | StarWars.ENERGYGLOBE_BIT | StarWars.BRICK_BIT |
                StarWars.ENEMY_BIT | StarWars.OBJECT_BIT | StarWars.LUKE_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){

        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }
}

