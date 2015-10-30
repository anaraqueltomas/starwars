package org.academia.nunocruz.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.academia.nunocruz.StarWars;

public class Hud implements Disposable {

    //Scenes2D Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Luke score/time Tracking Variables
    private Integer worldTimer;
    private float timeCount;
    private static Integer score;
    private static Integer energyPoints;

    //Scene2D widgets
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label lukeLabel;
    private Label energyPointsLabel;
    private static Label energyLabel;

    public Hud(SpriteBatch sb){

        //Definimos as nossas tracking variables
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        energyPoints = 20;

        //setup the HUD viewport using a new camera separate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(StarWars.V_WIDTH, StarWars.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,sb);

        Table table = new Table(); //por default vem alinhada ao centro do ecran (V_WIDTH/2, V_HEIGHT/2)
        table.top(); //vamos coloca-la no topo
        table.setFillParent(true); //a table é do tamanho do stage

        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE)); // Número de digitos, tipo de fonte e cor
        scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        energyLabel = new Label(String.format("%03d", energyPoints), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Time:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("Level:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lukeLabel = new Label("Score:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        energyPointsLabel = new Label ("Energy:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(lukeLabel).expandX().padTop(10); // o expandX partilha o espaço de igual forma no top, caso existam outras labels..
        table.add(scoreLabel).expandX().padTop(10);
        table.add(energyPointsLabel).expandX().padTop(10);
        table.add(energyLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(levelLabel).expandX().padTop(10);;
        table.add(timeLabel).expandX().padTop(10);
        table.add(countdownLabel).expandX().padTop(10);;
        //table.row(); //criamos uma nova linha para a nossa table

        //adicionamos a nossa table ao stage
        stage.addActor(table); //Adicionamos a table ao nosso stage
    }

    public void update(float delta){
        timeCount += delta;
        if(timeCount >= 1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public static void addEnergyPoints(int value){
        energyPoints += value;
        energyLabel.setText(String.format("%3d", energyPoints));
    }

    public static void subtractEnergyPoints(int value){
        energyPoints -= value;
        energyLabel.setText(String.format("%3d", energyPoints));
    }

    public static Integer getEnergyPoints() {
        return energyPoints;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
