package org.academia.nunocruz.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.academia.nunocruz.StarWars;
import org.academia.nunocruz.gameObjects.Luke;

public class GameOverScreen implements Screen{

    private Viewport viewport;
    private Stage stage;
    private Game game;
    private Texture img;

    public GameOverScreen(Game game){

        this.game = game;
        viewport = new FitViewport(StarWars.V_WIDTH, StarWars.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((StarWars) game).batch);

        img = new Texture(Gdx.files.internal("gameOver.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen((StarWars) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ((StarWars) game).batch.begin();
        ((StarWars) game).batch.draw(img, 10, 10);
        ((StarWars) game).batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }
}
