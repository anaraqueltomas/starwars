package org.academia.nunocruz.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.box2DTools.B2dContactListener;
import org.academia.nunocruz.box2DTools.B2dWorld;
import org.academia.nunocruz.gameObjects.Enemies.Enemy;
import org.academia.nunocruz.gameObjects.Luke;

public class PlayScreen implements Screen{

    //Reference to our Game, used to set Screens
    private StarWars game;
    private TextureAtlas atlas;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2dWorld creator;

    ////
    GameOverScreen gameOverScreen;
    FinishScreen finishScreen;

    public static boolean victory;

    private Sound gameOverSound;

    //Sprites
    public Luke luke;

    public PlayScreen(StarWars game){
        victory = false;

        atlas = new TextureAtlas("pack.atlas");

        this.game = game;

        //create cam used to follow Luke through cam world
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(StarWars.V_WIDTH / StarWars.PPM, StarWars.V_HEIGHT / StarWars.PPM, gamecam);

        //create our game HUD for scores/timers/level info
        hud = new Hud(game.batch);

        //////
        gameOverScreen = new GameOverScreen(game);

        finishScreen = new FinishScreen(game);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("Mapa/um.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1  / StarWars.PPM);

        //initially set our gamecam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight() / 2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10), true);

        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();
        luke = new Luke(this);

        creator = new B2dWorld(this);

        //create luke in our game world

        world.setContactListener(new B2dContactListener());

        if(!StarWars.music.isPlaying()) {
            StarWars.music.play();
        }

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){

        //control our luke using immediate impulses
         if(luke.currentState != Luke.State.DEAD) {

             if (Gdx.input.isKeyJustPressed(Input.Keys.UP))

                /** Ana: FLAG to stop multiple jumps */
                 if (luke.currentState != Luke.State.JUMPING) {
                     luke.jump();
                     luke.setCurrentState(Luke.State.JUMPING);
                 }

             if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && luke.b2body.getLinearVelocity().x <= 2)
                 luke.b2body.applyLinearImpulse(new Vector2(0.1f, 0), luke.b2body.getWorldCenter(), true);
             if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && luke.b2body.getLinearVelocity().x >= -2)
                 luke.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), luke.b2body.getWorldCenter(), true);

             if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
                 luke.lukeIsKilling = true;

         }
    }

    public void update(float dt){
        //handle user input first
        handleInput(dt);

        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        luke.update(dt);

        // Os inimigos só "acordam" quando estamos a menos de 260 metros deles;
        for(Enemy enemy : creator.getEnemies()) {
            enemy.update(dt);
            if(enemy.getX() < luke.getX() + 260 / StarWars.PPM) {
                enemy.b2body.setActive(true);
            }
        }

        hud.update(dt);

        //attach our gamecam to our players.x coordinate
        if(luke.currentState != Luke.State.DEAD) {
            gamecam.position.x = luke.b2body.getPosition().x;
        }

        //update our gamecam with correct coordinates after changes
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);

    }

    @Override
    public void render(float delta) {

        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin();

        //desenha o luke
        luke.draw(game.batch);

        //desenha os inimigos
        for (Enemy enemy : creator.getEnemies())
            enemy.draw(game.batch);

        game.batch.end();

        //Set our batch to now draw what the Hud camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        if(victory){
            game.setScreen(new FinishScreen(game));
            dispose();
        }
    }

    public boolean gameOver(){

        if(luke.currentState == Luke.State.DEAD && luke.getStateTimer() > 1

           // quando o tempo acaba
           || Hud.getWorldTimer() <= 0 /* && luke.getStateTimer() > 1 */
                
           // quando cai num buraco
           || luke.b2body.getPosition().y < 0 ) {

            StarWars.music.stop();

            gameOverSound = StarWars.manager.get("audio/sounds/gameover.wav");
            gameOverSound.play();

            Luke.health = 10;
            Luke.score = 0;
            return true;
        }
        return false;
    }


    @Override
    public void resize(int width, int height) {
        //updated our game viewport
        gamePort.update(width,height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
