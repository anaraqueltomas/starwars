package org.academia.nunocruz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.academia.nunocruz.screens.HomeScreen;

public class StarWars extends Game {

	//Virtual Screen size and Box2D Scale(Pixels Per Meter)
	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 256;
	public static final float PPM = 100;

	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short LUKE_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short ENERGYGLOBE_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short FIREBALL_BIT = 128;

	/** Ana */
	public static final short LIGHTSABER_BIT = 256;


	public SpriteBatch batch;

	public static AssetManager manager;

	@Override
	public void create () {

		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/gameMusic.wav", Music.class);
		manager.load("audio/sounds/energy.wav", Sound.class);
		manager.load("audio/sounds/gameover.wav", Sound.class);
		manager.finishLoading();

		setScreen(new HomeScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
}


