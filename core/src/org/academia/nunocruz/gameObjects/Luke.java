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
 * Created by nunocruz on 29/10/15.
 */
public class Luke extends Sprite {

    public enum State{ FALLING, JUMPING, STANDING, RUNNING, DEAD}
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;
    private boolean runningRight;
    private boolean lukeIsDead;

    public Luke(PlayScreen screen){

        super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to marioRun Animation
        for(int i=1; i<4; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 0, 16, 16));
        }
        marioRun = new Animation(0.1f, frames);
        frames.clear();

        //get jump animation frames and add them to marioJump Animation
        for(int i = 4; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 0, 16, 16));
        }
        marioJump = new Animation(0.1f, frames);

        //create texture region for mario standing
        marioStand = new TextureRegion(getTexture(),0,0,16,16);

        //define mario in Box2d
        defineMario();

        //set initial values for marios location, width and height. And initial frame as marioStand.
        setBounds(0,0,16/ StarWars.PPM,16 / StarWars.PPM);
        setRegion(marioStand);

    }

    public void update(float delta){

        //update our sprite to correspond with the position of our Box2D body
        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() / 2);

        //update sprite with the correct frame
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta){

        //get marios current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch (currentState){

            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            case FALLING:
            default:
                region = marioStand;
                break;
        }


        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x>0 || runningRight)&& region.isFlipX()){
            region.flip(true,false);
            runningRight = true;

        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + delta : 0;

        //update previous state
        previousState = currentState;

        //return our final adjusted frame
        return region;
    }

    public State getState(){
        //Test to Box2D for velocity on the X and Y-Axis
        //if mario is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if(b2body.getLinearVelocity().y > 0 || b2body.getLinearVelocity().y<0 && previousState == State.JUMPING ) {
            return State.JUMPING;
        }
        else if(lukeIsDead){
            return State.DEAD;
        }
            //if negative in Y-Axis mario is falling
        else if(b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        }
            //if mario is positive or negative in the X axis he is running
        else if(b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        }
            //if none of these return then he must be standing
        else{
            return State.STANDING;
        }

    }

    public void defineMario(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32/ StarWars.PPM,32/ StarWars.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ StarWars.PPM);
        fixtureDef.filter.categoryBits = StarWars.MARIO_BIT;
        fixtureDef.filter.maskBits = StarWars.GROUND_BIT | StarWars.COIN_BIT | StarWars.BRICK_BIT|
                StarWars.ENEMY_BIT| StarWars.OBJECT_BIT| StarWars.ENEMY_HEAD_BIT;

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/ StarWars.PPM, 6 / StarWars.PPM), new Vector2(2/StarWars.PPM, 6 /StarWars.PPM));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;

        b2body.createFixture(fixtureDef).setUserData("head");
    }

    public void hit(){

        /**
        StarWars.manager.get("audio/music/mario_music.ogg", Music.class).stop();
        StarWars.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
        lukeIsDead = true;
         */

    }

    public boolean isDead(){
        return lukeIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

}
