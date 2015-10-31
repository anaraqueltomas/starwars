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
	public static final short GROUND_BIT = 1;
	public static final short LUKE_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short ENERGYGLOBE_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short LIGHTSABER_BIT = 128;
	public static final short SPACESHIP_BIT = 256;
	public static final short YODA_BIT = 512;

	public SpriteBatch batch;

	public static AssetManager manager;
	public static Music music;

	@Override
	public void create () {

		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/gameMusic.wav", Music.class);
		//manager.load("audio/music/cantina.wav", Music.class);
		//manager.load("audio/sounds/catchEnergy.wav", Sound.class);
		manager.load("audio/sounds/gameover.wav", Sound.class);
		//manager.load("audio/sounds/Lightsaber Turn On.wav", Sound.class);
		//manager.load("audio/sounds/monster_creature_grunt-1.mp3", Sound.class);
		manager.load("audio/sounds/nave-endgame.wav", Sound.class);
		manager.load("audio/sounds/victorySound.mp3", Sound.class);
		manager.finishLoading();

		music = StarWars.manager.get("audio/music/gameMusic.wav", Music.class);
		music.setLooping(true);
		music.setVolume(0.3f);
		music.play();

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


