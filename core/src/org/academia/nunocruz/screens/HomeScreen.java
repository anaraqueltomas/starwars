package org.academia.nunocruz.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.academia.nunocruz.StarWars;

/**
 * Created by raqueldoria on 30/10/15.
 */
public class HomeScreen implements Screen {

    private Viewport viewport;
    private Stage stage;
    private Game game;
    private Texture img;


    public HomeScreen(Game game) {
        this.game = game;
        viewport = new FitViewport(StarWars.V_WIDTH, StarWars.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((StarWars) game).batch);

        img = new Texture(Gdx.files.internal("introScreen.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
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

    }
}
