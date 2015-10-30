package org.academia.nunocruz.gameObjects;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.screens.Hud;
import org.academia.nunocruz.screens.PlayScreen;

public class Luke extends Sprite {

    public enum State{ FALLING, JUMPING, STANDING, RUNNING, DEAD}
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;
    private TextureRegion lukeStand;
    private Animation lukeRun;
    private Animation lukeJump;
    private float stateTimer;
    private boolean runningRight;
    private boolean lukeIsDead;
    private static Integer energyPoints = 20;

    public Luke(PlayScreen screen){

        super(screen.getAtlas().findRegion("lukeStand"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to lukeRun Animation
        for(int i = 0; i < 3; i++){
            frames.add(new TextureRegion(getTexture(), i*32, 0, 32, 32));
        }
        lukeRun = new Animation(0.1f, frames);
        frames.clear();

        //get jump animation frames and add them to lukeJump Animation
        for(int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i*32, 0, 32, 32));
        }
        lukeJump = new Animation(0.1f, frames);

        //create texture region for luke standing
        lukeStand = new TextureRegion(getTexture(),256,64,32,32);

        //define mario in Box2d
        defineLuke();

        //set initial values for luke location, width and height. And initial frame as lukeStand.
        setBounds(0,0,32/ StarWars.PPM,32 / StarWars.PPM);
        setRegion(lukeStand);

    }

    public void update(float delta){

        //update our sprite to correspond with the position of our Box2D body
        setPosition(b2body.getPosition().x - getWidth() /2, b2body.getPosition().y - getHeight() / 2);

        //update sprite with the correct frame
        setRegion(getFrame(delta));

    }

    public TextureRegion getFrame(float delta){

        //get luke current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch (currentState){

            case JUMPING:
                region = lukeJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = lukeRun.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            case FALLING:
            default:
                region = lukeStand;
                break;
        }

        //if luke is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        }

        //if luke is running right and the texture isnt facing right... flip it.
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
        //if luke is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            return State.JUMPING;
        }
        else if(lukeIsDead){
            return State.DEAD;
        }
            //if negative in Y-Axis mario is falling
        else if(b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        }
            //if luke is positive or negative in the X axis he is running
        else if(b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        }
            //if none of these return then he must be standing
        else{
            return State.STANDING;
        }
    }

    public void defineLuke(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(64/ StarWars.PPM,64/ StarWars.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8/StarWars.PPM,16/StarWars.PPM);
        fixtureDef.filter.categoryBits = StarWars.LUKE_BIT;
        fixtureDef.filter.maskBits = StarWars.GROUND_BIT | StarWars.ENERGYGLOBE_BIT | StarWars.BRICK_BIT|
                StarWars.ENEMY_BIT| StarWars.OBJECT_BIT;

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);
    }

    public void hit(){

        StarWars.manager.get("audio/music/gameMusic.wav", Music.class).stop();
        //StarWars.manager.get("audio/sounds/gameover.wav", Sound.class).play();
        //lukeIsDead = true;
        subtractEnergyPoints(2);
    }

    public boolean isDead(){
        return lukeIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public void jump() {
        b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        currentState = State.JUMPING;
    }

    public static Integer getEnergyPoints() {
        return energyPoints;
    }

    public static void addEnergyPoints(int value){
        energyPoints += value;
        Hud.setEnergyLabel();
    }

    public static void subtractEnergyPoints(int value){
        energyPoints -= value;
        Hud.setEnergyLabel();
    }
}
